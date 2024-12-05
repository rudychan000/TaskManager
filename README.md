To start the frontend service: ```sudo systemctl start nginx```

Rebuild the frontend: 
``` 
npm run build
sudo cp -r build /var/www/react-app
sudo systemctl restart nginx
```


To start the backend service: ```sudo systemctl start backend.service```
