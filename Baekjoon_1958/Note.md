## 문제 설명
### LCS 알고리즘이란?
최장 공통 부분 수열(Longest Common Subseqeunce)를 의미함.
ex) ABCDEF, GBCDFE → BCDF

## 문제 풀이
- dp를 사용하여 문제 해결
- 3중 반복문을 통해 각각의 문자열을 반복하며 3개의 단어가 같다면( a[i] = b[j] = c[k] ) dp[i][j][k] = dp[i-1][j-1][k-1] + 1이다.
- 3개가 같지 않을 시 dp[i][j][k]는 dp[i-1][j][k], dp[i][j-1][k], dp[i][j][k-1] 중 가장 큰 값을 갖게 된다.
- 모든 반복을 마친 후 dp[aLen-1][bLen-1][cLen-1]이 LCS가 된다.
