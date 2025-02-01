package com.practice.java.lang;

import com.google.common.base.Charsets;
import com.google.common.io.ByteSource;
import com.google.common.io.CharSource;
import com.google.common.io.CharStreams;
import lombok.SneakyThrows;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.input.ReaderInputStream;
import org.apache.commons.lang3.StringUtils;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.html.HtmlParser;
import org.apache.tika.sax.BodyContentHandler;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.sql.Clob;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.StringJoiner;
import java.util.StringTokenizer;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StringTest {

    private static final Logger log = LoggerFactory.getLogger(StringTest.class);

    /**
     * s1=jack
     * s2=a
     */
    @Test
    void createString() {
        String s1 = new String("jack");
        log.info("s1={}", s1);

        String s2 = "a";
        log.info("s2={}", s2);
    }

    @Test
    public void stringFormatConventionsAndFlags() {
        String str1 = String.format("Welcome to number %d, %d, %d and %d", 1, 2, 3, 4);
        log.info("str1={}", str1); // str1=Welcome to number 1, 2, 3 and 4

        String str2 = String.format("Decimal value with 2 decimals: %.2f", 1.2356);
        log.info("str2={}", str2); // str2=Decimal value with 2 decimals: 1.24

        String str3 = String.format("%d, %o, %x, %X", 32, 32, 32, 32);
        log.info("str3={}", str3); // str3=32, 40, 20, 20

        String str4 = String.format("%d, %#o, %#x, %#X", 32, 32, 32, 32);
        log.info("str4={}", str4); // str4=32, 040, 0x20, 0X20

        log.info("-- right justify -- ");
        String str5 = String.format("num1:%4d, num2:%4d ", 6, 287);
        log.info("str5={}", str5); // str5=num1:   6, num2: 287

        String str6 = String.format("num1:%4d, num2:%4d ", 657, 7);
        log.info("str6={}", str6); // str6=num1: 657, num2:   7

        String str7 = String.format("num1:%04d, num2:%04d ", 877, 6);
        log.info("str7={}", str7); // str7=num1:0877, num2:0006

        log.info("-- left justify -- ");
        String str8 = String.format("num1:%-4d, num2:%-4d ", 6, 287);
        String str9 = String.format("num1:%-4d, num2:%-4d ", 657, 7);
        log.info("str8={}", str8); // str8=num1:6   , num2:287
        log.info("str9={}", str9); // str9=num1:657 , num2:7

        log.info("-- group separator");
        String str10 = String.format("%d", 1234567);
        String str11 = String.format("%,d", 1234567);
        String str12 = String.format("%,.2f", 123457.6);
        log.info("str10={}", str10); // str10=1234567
        log.info("str11={}", str11); // str11=1,234,567
        log.info("str12={}", str12); // str12=123,457.60

        String s13 = String.format("%d", 123);
        String s14 = String.format("%d", -123);
        String s15 = String.format("% d", 123);
        String s16 = String.format("% d", -123);
        String s17 = String.format("%+d", 123);
        String s18 = String.format("%+d", -123);
        String s19 = String.format("%(d", 123);
        String s20 = String.format("%(d", -123);
        String s21 = String.format("% (d", 123);
        log.info("s13={}", s13); // s13=123
        log.info("s14={}", s14); // s14=-123
        log.info("s15={}", s15); // s15= 123
        log.info("s16={}", s16); // s16=-123
        log.info("s17={}", s17); // s17=+123
        log.info("s18={}", s18); // s18=-123
        log.info("s19={}", s19); // s19=123
        log.info("s20={}", s20); // s20=(123)
        log.info("s21={}", s21); // s21= 123
    }

    @Test
    public void stringFormat() {
        String str = "Welcome to %s. Visit %s (URL - %s) for more details.";
        String finalString = String.format(str, "Java world", "Cerebro", "https://cerebroap.herokuapp.com/");

        // finalString: Welcome to Java world. Visit Cerebro (URL - https://cerebroap.herokuapp.com/) for more details.
        System.out.println("finalString: " + finalString);
    }

    /**
     * To use (%) percentage symbol in String.format we have to use %%(double percentage) as escape character
     * <p>
     * Input:
     * Ana got %s% percentage
     * Output:
     * java.util.UnknownFormatConversionException
     * <p>
     * Input:
     * Ana got %s%% percentage
     * Output:
     * Ana got 90% percentage
     */
    @Test
    public void usePercentageSymbolInStringFormat() {
        String str2 = "Ana got %s%% percentage";
        String result = String.format(str2, "90");
        System.out.println(result);
    }

    /**
     * Output: [hello, world, welcome, to, java]
     */
    @Test
    void splitBySpace() {
        String inputString = "hello world welcome to java";
        String[] splitBySpace = inputString.split("\\s+");
        log.info("{}", Arrays.deepToString(splitBySpace));
    }

    /**
     * [StringTest.splitToCharacters] - [a, b, c, d, e]
     */
    @Test
    void splitToCharacters() {
        String input1 = "abcde";
        String[] output1 = input1.split("");
        log.info("{}", Arrays.deepToString(output1));
    }

    @Test
    public void getbyteArrayFromString() {
        String string = "Hello World";

        // method 1
        byte[] byteArray1 = string.getBytes();

        // byteArray1: [B@41a4555e
        System.out.println("byteArray1: " + byteArray1);

        // Arrays.toString(byteArray1): [72, 101, 108, 108, 111, 32, 87, 111, 114, 108,
        // 100]
        System.out.println("Arrays.toString(byteArray1): " + Arrays.toString(byteArray1));

        // method 2
        byte[] byteArray2 = string.getBytes(Charset.forName("UTF-8"));

        // byteArray2: [B@3830f1c0
        System.out.println("byteArray2: " + byteArray2);

        // Arrays.toString(byteArray2): [72, 101, 108, 108, 111, 32, 87, 111, 114, 108,
        // 100]
        System.out.println("Arrays.toString(byteArray2): " + Arrays.toString(byteArray2));

        // method 3 - from java 7
        byte[] byteArray3 = string.getBytes(StandardCharsets.UTF_8);

        // byteArray3: [B@39ed3c8d
        System.out.println("byteArray3: " + byteArray3);

        // Arrays.toString(byteArray3): [72, 101, 108, 108, 111, 32, 87, 111, 114, 108,
        // 100]
        System.out.println("Arrays.toString(byteArray3): " + Arrays.toString(byteArray3));
    }

    @Test
    public void getAsciValueOfCharsInString() {
        String str = "abcdefghijklmnopqrstuvwxyz";
        byte[] strByteArray = str.getBytes();

        // asciValues: [97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109,
        // 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122]
        System.out.println("asciValues: " + Arrays.toString(strByteArray));
    }

    @Test
    public void convertByteArrayToString() {
        String str = "Hello World";
        byte[] byteArray = str.getBytes();

        // str: Hello World
        System.out.println("str: " + str);

        // byteArray: [B@41a4555e
        System.out.println("byteArray: " + byteArray);

        String strFromByteArray = new String(byteArray);

        // strFromByteArray: Hello World
        System.out.println("strFromByteArray: " + strFromByteArray);
    }

    @Test
    public void divideString() {
        /*
         * Hello Java World Welcome to Java
         */
        String str = "Hello-Java-World-Welcome-to-Java";
        String[] strs = str.split("-");
        for (String s : strs) {
            System.out.println(s);
        }

        // using StringTokenizer
        /*
         * Hello Java World Welcome to Java
         */
        System.out.println("------- Using StringTokenizer -------- ");
        StringTokenizer stringTokenizer = new StringTokenizer(str, "-");
        for (; ; stringTokenizer.hasMoreTokens()) {
            System.out.println(stringTokenizer.nextToken());
        }
    }

    @Test
    public void stringToCharArray() {
        String str = "Hello World";
        byte[] byteArray = str.getBytes();
        String byteString = Arrays.toString(byteArray);

        // [72, 101, 108, 108, 111, 32, 87, 111, 114, 108, 100]
        System.out.println(byteString);

        byteString = byteString.substring(1, byteString.length() - 1);

        // 72, 101, 108, 108, 111, 32, 87, 111, 114, 108, 100
        System.out.println(byteString);

        String[] asciValues = byteString.split(",");

        List<String> charsList = new ArrayList<>();

        for (String asciValue : asciValues) {
            String c = String.valueOf((char) Integer.parseInt(asciValue.trim()));
            charsList.add(c);
        }

        // [H, e, l, l, o, , W, o, r, l, d]
        System.out.println(charsList);
    }

    /**
     * Output: capitalStr: HELLO WORLD lowerStr: hello world
     */
    @Test
    public void convertCase() {
        String str = "hello world";
        String capitalStr = str.toUpperCase();
        System.out.println("capitalStr: " + capitalStr);

        String lowerStr = capitalStr.toLowerCase();
        System.out.println("lowerStr: " + lowerStr);
    }

    @Test
    public void capitalizeFirstChar() {
        String hello = "hello";
        String result = hello.substring(0, 1).toUpperCase() + hello.substring(1, hello.length());
        System.out.println(result); // Hello

        // using common-lang3
        String result2 = StringUtils.capitalize(hello);
        System.out.println(result2); // Hello
    }

    /**
     * Output: bcda cdab
     */
    @Test
    public void stringShift() {
        String s = "abcd";
        if (s.isEmpty()) {
            return;
        }
        int leftShift = 1;
        while (leftShift > s.length()) {
            leftShift = leftShift - s.length();
        }
        String leftShiftResult = s.substring(leftShift) + s.substring(0, leftShift);
        System.out.println(leftShiftResult);

        int rightShift = 3;
        int length = 0;
        if (leftShiftResult.length() > rightShift)
            length = leftShiftResult.length() - rightShift;
        else
            length = rightShift - leftShiftResult.length();

        String rightShiftResult = leftShiftResult.substring(length) + leftShiftResult.substring(0, length);
        System.out.println(rightShiftResult);
    }

    /**
     * Output: [wel, elc, lco, com, ome, met, eto, toj, oja, jav, ava] [welc, elco,
     * lcom, come, omet, meto, etoj, toja, ojav, java] [welco, elcom, lcome, comet,
     * ometo, metoj, etoja, tojav, ojava]
     */
    @Test
    public void printAllSubStringsOfSpecifiedLength() {
        String s = "welcometojava";

        // substring of length 3
        List<String> subStrings = new ArrayList<>();
        for (int i = 0; i <= s.length() - 3; i++) {
            subStrings.add(s.substring(i, i + 3));
        }
        System.out.println(subStrings);

        // substring of length 4
        List<String> subStrings2 = new ArrayList<>();
        for (int i = 0; i <= s.length() - 4; i++) {
            subStrings2.add(s.substring(i, i + 4));
        }
        System.out.println(subStrings2);

        // substring of length 5
        List<String> subStrings3 = new ArrayList<>();
        for (int i = 0; i <= s.length() - 5; i++) {
            subStrings3.add(s.substring(i, i + 5));
        }
        System.out.println(subStrings3);
    }

    /**
     * Output: strList: [Welcome, to, hacker, rank, test] sort by length: [to, rank,
     * test, hacker, Welcome]
     */
    @Test
    public void sortStringsInSentnceByLength() {
        String str = "Welcome to hacker rank test";
        String[] strs = str.split(" ");

        List<String> strList = Arrays.asList(strs);

        System.out.println("strList: " + strList);

        Comparator<String> lengthComparator = new Comparator<String>() {

            @Override
            public int compare(String o1, String o2) {
                return Integer.valueOf(o1.length()).compareTo(Integer.valueOf(o2.length()));
            }
        };

        Collections.sort(strList, lengthComparator);
        System.out.println("sort by length: " + strList);
    }

    /**
     * Example:
     * password: rC$6it
     * before-password: rC$6it
     * after-password: rC\$6it
     * str: This is your password: rC$6it
     */
    @Test
    public void replaceAll() {
        String str = "This is your password: {password}";
        // If password contains backslash(\) or dollar($) symbols then exception will be thrown so escape them with Matcher.quoteReplacement
        String password = "rC$6it";
        System.out.println("before-password:" + password); // before-password:rC$6it
        password = java.util.regex.Matcher.quoteReplacement(password);
        System.out.println("after-password:" + password); // after-password:rC\$6it
        str = str.replaceAll("\\{password\\}", password);
        System.out.println("str: " + str); // str: This is your password: rC$6it

        // Example 2
        String filePathWithName = "C:\\output-" + LocalDateTime.now().toString().replaceAll(":", "-") + ".txt";
        System.out.println(filePathWithName); // C:\output-2018-02-15T16-20-19.002.txt
    }

    /**
     * Output: Hello welcome to java String practice examples. Have Fun Hello
     * welcome to java String practice examples. Have Fun
     */
    @Test
    public void removeSpacesInString() {
        String str = "Hello  welcome to  java   String practice  examples.   Have Fun";

        // using for each
        String[] words = str.split(" ");
        StringBuffer sentence = new StringBuffer();
        Stream.of(words).forEach(word -> {
            if (!word.trim().isEmpty())
                sentence.append(word).append(" ");
        });
        System.out.println(sentence);

        // using JDK 8
        String sentence2 = Stream.of(words).filter(word -> !word.trim().isEmpty()).collect(Collectors.joining(" "));
        System.out.println(sentence2);
    }

    /**
     * Get Last N characters of String
     */
    @Test
    public void lastNCharactersOfString() {
        String alphabets = "abcdefghijklmnopqrstuvwxyz";

        // alphabets=abcdefghijklmnopqrstuvwxyz
        log.info("alphabets={}", alphabets);

        // last5Characters=vwxyz
        String last5Characters = lastNCharactersOfString(alphabets, 5);
        log.info("last5Characters={}", last5Characters);

        // last-10-characters=qrstuvwxyz
        String last10Characters = lastNCharactersOfString(alphabets, 10);
        log.info("last-10-characters={}", last10Characters);

        String last5Characters2 = StringUtils.right(alphabets, 5);
        log.info("{}", last5Characters2);
    }

    private String lastNCharactersOfString(String str, int n) {
        String result = str;
        if (Objects.nonNull(str) && str.length() > n) {
            result = str.substring(str.length() - n);
        }
        return result;
    }

    /**
     * Split the String
     */
    @Test
    public void split() {
        String input = "welcome, to String practice";

        log.info("-- split by space --");
        String[] split1 = input.split(" ");
        Arrays.stream(split1).forEach(str -> log.info("--{}--", str));

        log.info("-- split by word break --");
        String[] split2 = input.split("\\b");
        Arrays.stream(split2).forEach(str -> log.info("--{}--", str));

        log.info("-- split by word --");
        Arrays.stream(split2).filter(str -> str.matches("\\w+")).forEach(str -> log.info("--{}--", str));

        // split empty String
        String str = "";
        String[] str2 = str.split(",");
        System.out.println(Arrays.toString(str2));
        for (String str3 : str2) {
            System.out.println(str3);
        }
    }

    /**
     * While split by pipe symbol we need to give as "\\|" otherwise pipe will be considered as regex
     * <p>
     * split(regex) internally returns result of split(regex, 0) and in documentation of this method you can find (emphasis mine)
     * The limit parameter controls the number of times the pattern is applied and therefore affects the length of the resulting array.
     * If the limit n is greater than zero then the pattern will be applied at most n - 1 times, the array's length will be no greater than n, and the array's last entry will contain all input beyond the last matched delimiter.
     * If n is non-positive then the pattern will be applied as many times as possible and the array can have any length.
     * If n is zero then the pattern will be applied as many times as possible, the array can have any length, and trailing empty strings will be discarded.
     * <p>
     * Exception:
     * It is worth mentioning that removing trailing empty string makes sense only if such empty strings were created by the split mechanism. So for "".split(anything) since we can't split "" farther we will get as result [""] array.
     * It happens because split didn't happen here, so "" despite being empty and trailing represents original string, not empty string which was created by splitting process.
     */
    @Test
    public void splitByPipe() {
        String row = "1|2|||3||4|5|6|||";
        String[] strings = row.split("\\|", -1); // if -1 is not given empty strings will be ignored
        log.info("strings-size: {}", strings.length);
        System.out.println(Arrays.deepToString(strings));
    }

    @Test
    public void subString() {
        String alphabets = "abcdefghijklmnopqrstuvwxyz";

        /*
         * 5th character to end of String
         *
         * output: sub-string=fghijklmnopqrstuvwxyz
         */
        String subString1 = alphabets.substring(5);
        log.info("sub-string1={}", subString1);

        /*
         * 5th character to 9th (10-1) character
         *
         * output: sub-string2=fghij
         */
        String subString2 = alphabets.substring(5, 10);
        log.info("sub-string2={}", subString2);

        /**
         * Using StringUtils
         */
        String subString3 = StringUtils.substring(alphabets, 5, 50);
        System.out.println(subString3);
    }

    @Test
    public void stringToEBCDIC() {
        Charset charsetEBCDIC = Charset.forName("CP037");
        final String word = "Welcome to File IO";
        byte[] bytes = word.getBytes(charsetEBCDIC);
        log.info(Arrays.toString(bytes));
    }

    @Test
    public void stringToBytes() {
        final String word = "Hello File IO";
        byte[] bytes = word.getBytes();

        log.info("word-length={}, bytes-length={}", word.length(), bytes.length);
        for (int i = 0; i < word.length(); i++) {
            byte b = (byte) word.charAt(i);
            log.info("i={}, ch={}, b={}, ch={}", i, word.charAt(i), b, (char) b);
        }
    }

    /**
     * input=<html><head></head><body><b>Welcome to Java</b></body></head></html>
     * result=Welcome to Java
     */
    @Test
    public void removeHTMLFromString() {
        // using core java regex
        String input = "<html><head></head><body><b>Welcome to Java</b></body></head></html>";
        log.info("input={}", input);
        String result = input.replaceAll("\\<.*?\\>", "");
        log.info("result={}", result);

        // using tika parsers
        BodyContentHandler handler = new BodyContentHandler();
        Metadata metadata = new Metadata();
        ParseContext pcontext = new ParseContext();
        HtmlParser htmlparser = new HtmlParser();

        try {
            // input files - input.html, input-2.html
            FileInputStream inputstream = new FileInputStream(new File("src/main/resources/input.html"));
            htmlparser.parse(inputstream, handler, metadata, pcontext);
        } catch (IOException | SAXException | TikaException e) {
            e.printStackTrace();
        }
        log.info("Contents of the document:\n{}", handler.toString());
    }

    @SuppressWarnings("all")
    @SneakyThrows
    @Test
    public void stringToInputStream() {
        String input = "hello world";

        // using plain java
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());

        /*
         * using Guava - add dependency com.google.guava.guava
         */
        InputStream inputStream2 = new ReaderInputStream(CharSource.wrap(input).openStream());

        /*
         * using commons io
         */
        InputStream inputStream3 = IOUtils.toInputStream(input);

    }

    private static String intToValueKey(int value) {
        final StringBuilder sb = new StringBuilder(String.valueOf((char) ('A' + (value % 26))));
        while ((value = (value / 26 - 1)) >= 0) {
            sb.append((char) ('A' + (value % 26)));
        }
        return sb.reverse().toString();
    }

    @Test
    public void generateExcelCellNames() {
        StringJoiner result = new StringJoiner(",");

        for (int i = 0; i < 130; i++) {
            String columnIndex = intToValueKey(i);
            // System.out.println(columnIndex);
            result.add(columnIndex);
        }

        System.out.println(result);
    }

    /**
     * References: https://www.baeldung.com/convert-input-stream-to-string
     *
     * @throws IOException
     */
    @Test
    public void inputStreamToString() throws IOException {
        log.info("-------------- solution 1 using guava------------------------");
        String originalString = "hello world";
        InputStream inputStream = new ByteArrayInputStream(originalString.getBytes());
        ByteSource byteSource = new ByteSource() {
            @Override
            public InputStream openStream() throws IOException {
                return inputStream;
            }
        };

        String result = byteSource.asCharSource(Charsets.UTF_8).read();
        log.info("result={}", result);

        log.info("--------------- solution 2 using guava ---------------------");
        String originalString2 = "hello world 2";
        InputStream inputStream2 = new ByteArrayInputStream(originalString2.getBytes());
        String result2 = null;
        try (Reader reader = new InputStreamReader(inputStream2)) {
            result2 = CharStreams.toString(reader);
        }
        log.info("result2={}", result2);

        log.info("--------------- solution 3 using commons io ---------------------");
        String originalString3 = "hello world 3";
        InputStream inputStream3 = new ByteArrayInputStream(originalString3.getBytes());
        String result3 = IOUtils.toString(inputStream3, StandardCharsets.UTF_8.name());
        log.info("result3={}", result3);

        log.info("--------------- solution 4 using commons io ---------------------");
        String originalString4 = "hello world 4";
        InputStream inputStream4 = new ByteArrayInputStream(originalString4.getBytes());
        StringWriter result4 = new StringWriter();
        String encoding = StandardCharsets.UTF_8.name();
        IOUtils.copy(inputStream4, result4, encoding);
        log.info("result4={}", result4.toString());

        log.info("------------- solution 5 using InputStream -----------------------");
        String originalString5 = "hello world 5";
        InputStream inputStream5 = new ByteArrayInputStream(originalString5.getBytes());
        StringBuilder result5 = new StringBuilder();
        try (Reader reader = new BufferedReader(
                new InputStreamReader(inputStream5, Charset.forName(StandardCharsets.UTF_8.name())))) {
            int i = 0;
            while ((i = reader.read()) != -1) {
                result5.append((char) i);
            }
        }
        log.info("result5={}", result5);

        log.info("-------------- solution 6 using java 8 --------------------------------");
        String originalString6 = "hello world 6";
        InputStream inputStream6 = new ByteArrayInputStream(originalString6.getBytes());
        String result6 = new BufferedReader(new InputStreamReader(inputStream6, StandardCharsets.UTF_8.name())).lines()
                .collect(Collectors.joining("\n"));
        log.info("result6={}", result6);

        log.info("-------------- solution 7 using java scanner--------------------------------");
        String originalString7 = "hello world 7";
        InputStream inputStream7 = new ByteArrayInputStream(originalString7.getBytes());
        String result7 = null;
        try (Scanner scanner = new Scanner(inputStream7, StandardCharsets.UTF_8.name())) {
            result7 = scanner.useDelimiter("\\A").next();
        }
        log.info("result7={}", result7);

        log.info("---------------- solution 8 ByteArrayOutputStream ---------------------------");
        String originalString8 = "hello world 8";
        InputStream inputStream8 = new ByteArrayInputStream(originalString8.getBytes());
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        int nRead;
        byte[] data = new byte[1024];
        while ((nRead = inputStream8.read(data, 0, data.length)) != -1) {
            byteArrayOutputStream.write(data, 0, nRead);
        }
        byteArrayOutputStream.flush();
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        String result8 = new String(byteArray, StandardCharsets.UTF_8);
        log.info("result8={}", result8);

        log.info("---------------- solution 9 java.nio ---------------------------");
        String originalString9 = "hello world 9";
        InputStream inputStream9 = new ByteArrayInputStream(originalString9.getBytes());
        Path tempFile = Files.createTempDirectory("").resolve(UUID.randomUUID().toString() + ".tmp");
        Files.copy(inputStream9, tempFile, StandardCopyOption.REPLACE_EXISTING);
        String result9 = new String(Files.readAllBytes(tempFile));
        log.info("result9={}", result9);
    }

    private String rotate(String input, String direction, int noOfRotations) {
        int inputStringLength = input.length();

        if (inputStringLength == noOfRotations) {
            return input;
        }

        if (noOfRotations != 1 && (inputStringLength > noOfRotations) && (inputStringLength % noOfRotations) == 0) {
            return input;
        }

        if (noOfRotations > inputStringLength) {
            noOfRotations = noOfRotations - inputStringLength;
        }

        String str1 = "";
        String str2 = "";
        if ("L".equalsIgnoreCase(direction)) {
            str1 = input.substring(0, noOfRotations);
            str2 = input.substring(noOfRotations);
        } else {
            str1 = input.substring(0, (inputStringLength - noOfRotations));
            str2 = input.substring((inputStringLength - noOfRotations));
        }

        return str2 + str1;
    }

    /**
     * abcde, left, 2 - cdeab abcde, right, deabc
     */
    @Test
    public void rotateString() {
        String input = "abcde";
        String result1 = rotate(input, "L", 10);
        String result2 = rotate(input, "R", 10);

        System.out.println(result1);
        System.out.println(result2);
    }

    @Test
    public void clobToString() {
        clobToString(null);
    }

    private void clobToString(Clob clob) {

        if (clob == null) {
            return;
        }

        java.io.Writer w = new java.io.StringWriter();

        java.io.Reader in = null;
        try {
            in = clob.getCharacterStream();
            org.apache.logging.log4j.core.util.IOUtils.copy(in, w);
            w.toString();
        } catch (java.sql.SQLException | java.io.IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    @Test
    public void removeStringFromEnd() {
        log.info("{}", StringUtils.removeEnd(null, "*")); //null
        log.info("{}", StringUtils.removeEnd("", "*")); // ""
        log.info("{}", StringUtils.removeEnd("*", null)); // *
        log.info("{}", StringUtils.removeEnd("www.domain.com", ".com.")); // www.domain.com
        log.info("{}", StringUtils.removeEnd("www.domain.com", ".com")); // www.domain
        log.info("{}", StringUtils.removeEnd("www.domain.com", "domain")); // www.domain.com
        log.info("{}", StringUtils.removeEnd("abc", "")); // abc
        log.info("{}", StringUtils.removeEnd("abc,", ",")); // abc
    }

    @Test
    public void readOccurrenceOfCharFromLast() {
        String url = "https://www.employees.com/list/2018/employees";
        // extract /2018/employees string from url
        String str1 = url.substring(StringUtils.lastOrdinalIndexOf(url, "/", 2));
        log.info("str1={}", str1); // str1=/2018/employees

        String str2 = url.substring(StringUtils.lastOrdinalIndexOf(url, "/", 2) + 1);
        log.info("str2={}", str2); // str2=2018/employees
    }
}
