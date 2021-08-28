# Planning

## Core features

### Service (web scraper www.mangareader.com)
[x] Create service to search mangas  
[x] Create endpoint to get manga info 
[x] Create endpoint to get chapter images 

### Api (outgoing api)
[x] Setup reitit router (swagger/coercion)  
[x] define endpoints  
* [GET] /manga-api/manga/{mangaid}
* [GET] /manga-api/manga/{mangaid}/chapters
* [GET] /manga-api/manga/{mangaid}/chapters/{chapter}
* [GET] /manga-api/search/term?page=
* [GET] /manga-api/autocomplete/term

## Dev Ops
[ ] Github pipeline to run tests  
[ ] Deploy to heroku  
[ ] utilize docker/docker-compose  
