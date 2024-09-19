## 문제 설명
입력으로 건물을 짓는데 걸리는 시간과 우선적으로 지어져 있어야 하는 건물의 정보가 주어진다.
각 건물을 짓는데 걸리는 시간을 구하는 문제이다.

## 문제 풀이
### 주요 변수
1. buildingInfo → BuildInfo 객체를 가지는 Queue
2. isAlreadyBuild → 건물이 지어졌는지 체크할 Boolean 변수
3. buildTime → 건물을 짓는데 걸린 시간을 저장하는 변수

buildingInfo라는 Queue가 빌 때까지 반복을 한다.  
현재 정보를 poll로 꺼내어 우선적으로 지어져 있어야 하는 건물이 모두 지어져 있다면 (isAlreadyBuild의 우선순위 건물이 모두 true) 그 중 가장 오래 걸린 시간에
현재 건물을 짓는데 필요한 시간을 더해 buildTime에 저장해준다.  
Queue가 비게 되면 모든 건물을 다 지은 것이므로 각각 짓는데 걸린 시간을 출력해준다.
