## 문제
https://www.acmicpc.net/problem/2661

## 문제 설명
- 1, 2, 3으로만 이루어지는 수열
- 두 개의 부분 수열이 동일한 것이 있으면 나쁜 수열, 그렇지 않다면 좋은 수열
- 길이가 N인 가장 작은 좋은 수열 구하기

## 문제 풀이
- dfs 사용
- 빈 문자열에 1부터 더해주기 시작한다.
```
for(i in 1 .. 3) {
    str += ('0' + i)
    if(check() && dfs(depth + 1)) return true
    str = str.substring(0, str.length - 1)
}
```
- check() 메서드 = 동일한 부분 수열이 검사하는 메서드(동일한 부분 수열이 있다면 false 반환, 아니라면 true 반환)
- check가 true이면서 dfs의 다음 depth가 true를 반환한다면 추가한 문자 제거하지 않음.
- 아니라면 추가한 문자 제거
- depth가 길이와 같아지면 출력
