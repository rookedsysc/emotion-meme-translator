version: '3'
services:
  frontend:
    build:
      context: ./dockerfile_react   # 리액트 앱의 경로
      dockerfile: Dockerfile-frontend
    ports:
      - "80:80"         # 리액트 앱 포트 매핑
    volumes:
      - ./frontend:/app     # 로컬 디렉토리를 컨테이너에 마운트하여 실시간 코드 변경 반영 (개발용)
    depends_on:
      - backend             # 백엔드 서비스가 먼저 시작되어야 함

  backend:
    build:
      context: ./dockerfile_spring    # 스프링 앱의 경로
      dockerfile: Dockerfile-backend
    ports:
      - "8080:8080"         # 스프링 앱 포트 매핑
    volumes:
      - ./backend:/app      # 로컬 디렉토리를 컨테이너에 마운트하여 실시간 코드 변경 반영 (개발용)
