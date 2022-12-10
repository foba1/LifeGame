# class ImagePattern

이미지 파일을 패턴으로 변환하는 기능을 제공하는 클래스.

## Description

이 클래스는 사용자에게 이미지 파일(png, jpg, jpeg 등)을 원본 비율을 유지하며 CellBoard의 사이즈에 맞게 변환해 CellBoard에 출력할 수 있는 형태로 변환하는 기능을 제공한다.

## Member functions

### imageToPattern

```java
public static boolean[][] imageToPattern(File in);
```

파라미터로 받은 이미지 파일을 64 * 64 boolean 2차원 배열로 변환한다. 이미지에서 width와 height 중 큰 값을 64로 변환된다. 이 때, 원본 이미지 비율은 그대로 유지된다. 컬러 이미지도 변환 가능하다.