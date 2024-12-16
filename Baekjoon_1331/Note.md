## 문제
https://www.acmicpc.net/problem/1331

## 문제 설명
체스알 중 나이트를 사용하여 가로 6 세로 6 크기인 체스판을 모두 한번씩만 방문해야 하며, 끝점에서 시작점으로 돌아올 수 있어야 한다.

## 문제 풀이
1. 나이트가 움직인 순으로 입력이 주어진다.
2. 나이트가 움직이는 칸은 (수직 - 2, 수평 - 1) 혹은 (수직 - 1, 수평 - 2)이다.
3. 즉, 이전 칸과 현재 칸이 2번의 조건을 만족하는지 확인하고, 충족하지 못한다면 Invalid를 출력하도록 한다.
4. 모두 한번씩만 방문해야 하므로 방문 체크를 해줘야 하며, 이미 방문한 칸일 시 Invalid를 출력하도록 한다.
5. 36번째까지 통과했다면, 첫번째인 시작점으로 돌아갈 수 있는지 체크한다.