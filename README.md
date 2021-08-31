# mangareader-clj-api

Do you love reading mangas ? 
Here is a Manga API that provides endpoints to search and 
fetch manga contents. Under the hood we the information is
scraped from https://mangareader.tv/ website.

This is a toy project that I worked on learn new clojure libraries. 

## Requirements

To build the project you will need
* leiningen
* java 1.8jdk

## Running the application

Directly running with the run command

    $ lein run {?port}

Building uberjar and running the jar

    $ lein uberjar
    $ java -jar target/uberjar/mangareader-clj-api.jar {?port}

## Running tests

Run tests 

    $ lein bat-test once
    
Run tests and watch for changes

    $ lein bat-test auto

## Usage

Run the application with the above methods. 
You can access the swagger page on /manga-api/api-docs/index.html

You can search a term by  
__/manga-api/search/{term}{?page=0}__  
*/manga-api/search/bleach*
```json
{
  "source": "https://mangareader.tv/search/?w=bleach&page=1",
  "offset": 1,
  "totalPages": 1,
  "mangas": [
    {
      "id": "manga-qq951425",
      "chapters": 688,
      "title": "Bleach",
      "thumbnail": "https://mangareader.tv/mangaimage/manga-qq951425.jpg"
    },
    {
      "id": "manga-lx988480",
      "chapters": 47,
      "title": "Bleach - Digital Colored Comics",
      "thumbnail": "https://mangareader.tv/mangaimage/manga-lx988480.jpg"
    }
  ]
}
```
With the manga id you can search the information related to the manga  
__/manga-api/manga/{manga-id}__  
*/manga-api/manga/manga-qq951425* 
```json
{
  "id": "manga-qq951425",
  "title": "Bleach",
  "genres": [
    "Action",
    "Adult",
    "Adventure",
    "Comedy",
    "Doujinshi",
    "Drama",
    "Fantasy",
    "Horror",
    "Martial arts",
    "Shounen",
    "Supernatural"
  ],
  "chapters": 688,
  "source": "https://mangareader.tv/manga/manga-qq951425"
}
```
Or fetch the list of chapters of the manga
__/manga-api/manga/{manga-id}/chapters__  
*/manga-api/manga/manga-qq951425/chapters*
```json
{
  "id": "manga-qq951425",
  "total": 688,
  "chapters": [
    {
      "source": "https://mangareader.tv/chapter/manga-qq951425/chapter-0",
      "title": "Chapter 0 : One Shot - Pilot",
      "id": "chapter-0"
    },
    {
      "source": "https://mangareader.tv/chapter/manga-qq951425/chapter-1",
      "title": "Chapter 1 : Death And Strawberry",
      "id": "chapter-1"
    },
    {
      "source": "https://mangareader.tv/chapter/manga-qq951425/chapter-2",
      "title": "Chapter 2 : Starter",
      "id": "chapter-2"
    },
    {
      "source": "https://mangareader.tv/chapter/manga-qq951425/chapter-3",
      "title": "Chapter 3 : Headhittin'",
      "id": "chapter-3"
    },
    "..all the until the last chapter.."
  ]
}
```
Or you can fetch the pages of each chapter.
__/manga-api/manga/{manga-id}/chapters/{chapter-id}__   
*/manga-api/manga/manga-qq951425/chapters/chapter-1*
```json
{
  "mangaId": "manga-qq951425",
  "chapterId": "chapter-1",
  "title": "Chapter 1 : Death And Strawberry",
  "contents": [
    "http://cm.blazefast.co/93/80/9380dc5c1fc4526ae3a1d2503c70e6cf.jpg",
    "http://cm.blazefast.co/ef/91/ef91bf90eb5b72ad5bd0ef8ce4109e3d.jpg",
    "http://cm.blazefast.co/d0/04/d00445c78ea976d46e66b44728ba93c2.jpg",
    "http://cm.blazefast.co/33/41/33418f981f6efe68fb59ace347329b87.jpg",
    "http://cm.blazefast.co/03/be/03beef8b70e2e2aef48d87ca390c16be.jpg",
    "http://cm.blazefast.co/a9/de/a9dea98678d90b033d7ff1fadad98a95.jpg",
    "http://cm.blazefast.co/31/f5/31f5dc17b391973842bc676b53cae42e.jpg",
    "http://cm.blazefast.co/e7/fe/e7fec1c02e2e9d126e79636b8d67281b.jpg",
    "http://cm.blazefast.co/d4/61/d461023079ea1fa1301b7576aeb98c13.jpg",
    "http://cm.blazefast.co/a5/4b/a54b1f61cf3a799b27d81eb0adfe14da.jpg",
    "http://cm.blazefast.co/83/80/83808e8c50b4841a376f2ecde7c8391a.jpg",
    "http://cm.blazefast.co/c7/91/c7917d63b05370958ea6732697d7396f.jpg",
    "http://cm.blazefast.co/a5/61/a5616f0dc5c6bb7e30e77bc35e398134.jpg",
    "http://cm.blazefast.co/0d/5b/0d5b593edfda3e5df73badf98a1550cf.jpg",
    "http://cm.blazefast.co/52/a0/52a028e21bfe7ea44bc4afaa35fb5348.jpg",
    "http://cm.blazefast.co/56/31/563169e009a3c3b31d788319548ef678.jpg",
    "http://cm.blazefast.co/4e/cf/4ecfa2fd0940379163b2194fcffd0ff4.jpg",
    "http://cm.blazefast.co/0b/c5/0bc55e2a99f6bc1f2e3c3baf4fbd1ccb.jpg",
    "http://cm.blazefast.co/89/2a/892ab073c88a77703df909c526a8a67e.jpg",
    "http://cm.blazefast.co/61/57/6157c4600759df652650e798905339be.jpg",
    "http://cm.blazefast.co/27/aa/27aa4b1f4d250db4ca111daba96b12e7.jpg",
    "http://cm.blazefast.co/8e/14/8e14b2da0f9b16bcde71277fade84edd.jpg",
    "http://cm.blazefast.co/80/84/808426a630239ad067982d4313517fa6.jpg",
    "http://cm.blazefast.co/00/20/0020ee330e36cc877bbbd07e5b11e256.jpg",
    "http://cm.blazefast.co/89/e4/89e4f15360d08343e2a9bb4806f2bc5f.jpg",
    "http://cm.blazefast.co/2d/36/2d36cd2ef4825025afb481a289202498.jpg",
    "http://cm.blazefast.co/30/14/30149cf65ac3aa694641adfd5f85f0c0.jpg",
    "http://cm.blazefast.co/8e/bd/8ebddaa656f97c3202aaa0773e5fce87.jpg",
    "http://cm.blazefast.co/98/29/9829a08ea3066a9c7203613165c7da03.jpg",
    "http://cm.blazefast.co/fc/90/fc903e2c9f752ccfd9e1c7d99df034ed.jpg",
    "http://cm.blazefast.co/1c/d3/1cd3a5a2ff0395fc4de02cb13bff713c.jpg",
    "http://cm.blazefast.co/68/df/68dfebd4854d536856710c476b68acf7.jpg",
    "http://cm.blazefast.co/a4/f7/a4f7fbb17612e5f0604d069f3cf2ac28.jpg",
    "http://cm.blazefast.co/ae/1e/ae1e82808d0999c19be74b971bace1e2.jpg",
    "http://cm.blazefast.co/aa/01/aa0110ad39b267c7f7ffa1fde0ab707d.jpg",
    "http://cm.blazefast.co/01/95/0195d3773ec7668bd518a611cf48f2f5.jpg",
    "http://cm.blazefast.co/27/b2/27b2f6c5082b208fac837b91ebd09d6a.jpg",
    "http://cm.blazefast.co/fc/15/fc15a789c534213e2649ce7070ceaffe.jpg",
    "http://cm.blazefast.co/23/f3/23f32615ee182370cc24df8f7a57fc81.jpg",
    "http://cm.blazefast.co/e1/0b/e10bbe40c25adc0478bc8de861f00160.jpg",
    "http://cm.blazefast.co/d6/14/d614809cca40e08acf26080849f29f0f.jpg",
    "http://cm.blazefast.co/a4/01/a40122cc591093ce01fb5789bf7ff7fe.jpg",
    "http://cm.blazefast.co/0b/d2/0bd2402915714f9194cf0c42f71e6f46.jpg",
    "http://cm.blazefast.co/e0/15/e0155687e4a1f39caa3574cfacbae6fd.jpg",
    "http://cm.blazefast.co/3f/aa/3faa347f3cefabb5631dbe5f92aa26f4.jpg",
    "http://cm.blazefast.co/96/d7/96d73e9a1ee82f24f6df769d1d0ef078.jpg",
    "http://cm.blazefast.co/a5/8c/a58c14bfb7b2831c63ead247327b407a.jpg",
    "http://cm.blazefast.co/d1/ff/d1ff551f68711149159e013d25880e93.jpg",
    "http://cm.blazefast.co/3d/68/3d68d5c17aed46aa11d8ffeda143fcaa.jpg",
    "http://cm.blazefast.co/d3/ae/d3ae79859ad8c7615ccd63548be0ab03.jpg",
    "http://cm.blazefast.co/8a/2c/8a2c5282e130f941250fbe97b8e79aa2.jpg",
    "http://cm.blazefast.co/ae/99/ae99d7df8dda8bb209bbc4e1660a55f7.jpg",
    "http://cm.blazefast.co/ed/8a/ed8ab44e8805458921abdedb64a93f63.jpg",
    "http://cm.blazefast.co/6f/b2/6fb26490396223ed317219256223cf8c.jpg",
    "http://cm.blazefast.co/a6/e1/a6e1841e9c5dd050348b8289c57d6825.jpg",
    "http://cm.blazefast.co/b3/06/b306ac7fd012fdb03ba03cc78b950f67.jpg",
    "http://cm.blazefast.co/45/ad/45ade0c2bef930ed1d9ef494e17bfac3.jpg"
  ],
  "source": "https://mangareader.tv/chapter/manga-qq951425/chapter-1"
}
```

## License

Copyright © 2021 FIXME

This program and the accompanying materials are made available under the
terms of the Eclipse Public License 2.0 which is available at
http://www.eclipse.org/legal/epl-2.0.

This Source Code may also be made available under the following Secondary
Licenses when the conditions for such availability set forth in the Eclipse
Public License, v. 2.0 are satisfied: GNU General Public License as published by
the Free Software Foundation, either version 2 of the License, or (at your
option) any later version, with the GNU Classpath Exception which is available
at https://www.gnu.org/software/classpath/license.html.
