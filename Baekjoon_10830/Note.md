## 문제
https://www.acmicpc.net/problem/10830

## 문제 설명
크기가 n * n인 행렬 A가 있다.
A의 B제곱을 구하면 된다.
이때 수가 매우 커질 수 있으므로 A^B의 각 원소를 1000으로 나눈 나머지를 출력한다.

## 문제 풀이  
### 지수가 짝수일 때  
ex) A^4
A^4 = A^2 * A^2 = (A^1 * A^1) * (A^1 * A^1)

### 지수가 홀수일 때
ex) A^5
A^5 = A^4 * A^1 = (A^2 * A^2) * A^1
= (A^1 * A^1) * (A^1 * A^1) * A^1

이를 그래프로 보면 다음과 같다.
![image](https://github.com/user-attachments/assets/4dba0720-1a84-449b-a7f0-52a4726cda0c)
