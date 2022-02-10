FROM node:16-alpine as build

# Install Angular CLI and Cordova CLI
RUN npm install -g @angular/cli
RUN ng config -g cli.warnings.versionMismatch false

# Setup environment
ENV HOME=/home/usr/app
RUN mkdir -p $HOME
WORKDIR $HOME

# Download npm packages
COPY package.json package-lock.json $HOME
RUN npm install

# Build web-sources then android
COPY . $HOME
RUN ng build --configuration production

# Deploy to nginx server
FROM nginx:latest as deploy
COPY nginx.conf /etc/nginx/nginx.conf
COPY --from=build /home/usr/app/dist/expense-tracker-web-client /usr/share/nginx/html
EXPOSE 80
