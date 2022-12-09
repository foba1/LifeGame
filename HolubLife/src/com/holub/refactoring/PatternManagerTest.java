package com.holub.refactoring;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.BorderLayout;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PatternManagerTest {

	@BeforeAll
	public static void Initialize() {
		Life.main(null);
	}
	
	@BeforeEach
	void ClearCellBoard() {
		try {
			Field field = Universe.instance().getClass().getDeclaredField("outermostCell");
			field.setAccessible(true);
			CellBoard board = (CellBoard)field.get(Universe.instance());
			board.clear();
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	void SelectTest() {
		try {
			Method method = PatternManager.instance().getClass().getDeclaredMethod("SelectPattern", int.class);
			method.setAccessible(true);
			method.invoke(PatternManager.instance(), 2);
			
			Field field = PatternManager.instance().getClass().getDeclaredField("curPattern");
			field.setAccessible(true);
			int value = (int)field.get(PatternManager.instance());
			assertEquals(2, value);
			
			method.invoke(PatternManager.instance(), 2);
			
			value = (int)field.get(PatternManager.instance());
			assertEquals(-1, value);
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	void PreviewTest() {
		Boolean[][] beforeCellBoard = Universe.instance().getCellBoard();
		
		PatternManager.instance().Show(32, 32);
		Boolean[][] afterCellBoard = Universe.instance().getCellBoard();
		assertTrue(IsSameCellBoard(beforeCellBoard, afterCellBoard));
		
		try {
			Method method = PatternManager.instance().getClass().getDeclaredMethod("SelectPattern", int.class);
			method.setAccessible(true);
			method.invoke(PatternManager.instance(), 0);
			
			Field field = PatternManager.instance().getClass().getDeclaredField("curPattern");
			field.setAccessible(true);
			int value = (int)field.get(PatternManager.instance());
			assertEquals(0, value);
			
			PatternManager.instance().Show(32, 32);
			afterCellBoard = Universe.instance().getCellBoard();
			assertFalse(IsSameCellBoard(beforeCellBoard, afterCellBoard));
			
			PatternManager.instance().Reset();
			afterCellBoard = Universe.instance().getCellBoard();
			assertTrue(IsSameCellBoard(beforeCellBoard, afterCellBoard));
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		}
	}
	
	private boolean IsSameCellBoard(Boolean[][] array1, Boolean[][] array2) {
		for (int i = 0; i < array1.length; i++) {
			for (int j = 0; j < array1.length; j++) {
				if (array1[i][j] == array2[i][j]) continue;
				else return false;
			}
		}
		return true;
	}

}
