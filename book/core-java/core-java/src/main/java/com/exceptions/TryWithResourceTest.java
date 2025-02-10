package com.exceptions;

import org.junit.jupiter.api.Test;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TryWithResourceTest {

	/**
	 * Hello from MyAutoCloseable
	 * MyAutoCloseable close method
	 */
	@Test
	public void tryWithResource() {
		try (MyAutoCloseable myAutoCloseable = new MyAutoCloseable()) {
			myAutoCloseable.hello();
		} catch (Exception e) {
			log.error("{} - {}", e.getClass().getName(), e.getMessage());
		}
	}

	/**
	 * Hello from MyAutoCloseable
	 * java.lang.RuntimeException - Exception from MyAutoCloseable close method
	 */
	@Test
	public void tryWithResourceCloseThrewException() {
		try (MyAutoCloseable2 myAutoCloseable = new MyAutoCloseable2()) {
			myAutoCloseable.hello();
		} catch (Exception e) {
			log.error("{} - {}", e.getClass().getName(), e.getMessage());
		}
	}

	/**
	 * java.lang.RuntimeException - Exception from MyAutoCloseable hello method
	 * number of suppressed exceptions=1
	 * java.lang.RuntimeException - Exception from MyAutoCloseable close method
	 */
	@Test
	public void tryWithResourceCloseAndActualMethodThrewException() {
		try (MyAutoCloseable3 myAutoCloseable = new MyAutoCloseable3()) {
			myAutoCloseable.hello();
		} catch (Exception e) {
			log.error("{} - {}", e.getClass().getName(), e.getMessage());

			log.info("number of suppressed exceptions={}", e.getSuppressed().length);
			for (Throwable t : e.getSuppressed()) {
				log.error("{} - {}", t.getClass().getName(), t.getMessage());
			}
		}
	}

}