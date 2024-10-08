## 문제
https://www.acmicpc.net/problem/9342

## 문제 설명
1. 문자열은 {A, B, C, D, E, F} 중 0개 또는 1개로 시작해야 한다.
2. 그 다음에는 A가 하나 또는 그 이상 있어야 한다.
3. 그 다음에는 F가 하나 또는 그 이상 있어야 한다.
4. 그 다음에는 C가 하나 또는 그 이상 있어야 한다.
5. 그 다음에는 {A, B, C, D, E, F} 중 0개 또는 1개가 있으며, 더 이상의 문자는 없어야 한다.
위의 조건을 모두 충족하면 Infected!를 출력, 아니라면 Good를 출력한다.

## 문제 풀이
정규식을 이용하면 쉽다.
- A~F 중 문자가 0개 또는 1개 있다. → [A-F]?
- A가 하나 이상 있다 → A+
- F가 하나 이상 있다 → F+
- C가 하나 이상 있다 → C+
- 더 이상의 문자가 없다 → $

즉, 다음과 같은 졍규식이 나온다.
```
val reg = "[A-F]?A+F+C+[A-F]?$".toRegex()
```
