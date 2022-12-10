# class PatternManager

사용자가 빠르고 편리하게 다양한 패턴을 그릴 수 있도록 기능을 제공하는 클래스.

## Description

이 클래스는 사용자에게 다양한 패턴을 제공한다.

## Member variables

### curPattern

```java
private int curPattern;
```

현재 선택된 패턴의 index 값을 저장한다.

### curCellBoard

```java
private boolean[][] curCellBoard;
```

패턴 미리보기 기능을 위한 값으로, 미리보기 패턴을 제외한 CellBoard의 상태를 저장한다.

### pattern

```java
private Pattern[] pattern;
```

패턴 클래스들을 저장하는 배열이다.

## Member functions

### Reset

```java
public void Reset();
```

마우스가 화면을 벗어 났을 때 호출되며, CellBoard의 상태를 저장되어 있는 값으로 초기화한다.

### Show

```java
public void Show(int row, int column);
```

마우스 위치에 현재 선택된 패턴을 나타내는 함수이다.

### Draw

```java
public void Draw(int row, int column);
```

마우스 클릭 위치에 패턴을 그리는 함수이다.

### IsPatternSelected

```java
public boolean IsPatternSelected();
```

현재 패턴이 선택된 상태인지를 확인하고, 결과로 반환한다.

### SelectPattern

```java
private void SelectPattern(int pattern);
```

패턴을 선택하는 함수이다.