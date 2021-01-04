FROM node:6.14.2
EXPOSE 8080
COPY src/index.js .
CMD node index.js