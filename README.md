<h2 align="center">FullRest проект веб-приложения "Новости"</h2>

В данном приложении предоставлен CRUD интерфейс back-end системы управления новостями со связью "с внешним миром" через REST API.<p>
Проект находится на стадии разработки. <p>
В проекте используется Spring Boot версии 3.1.4. 

Разработаны API согласно REST подходу:
<ul>
<li>CRUD для работы с новостью;</li>
<li>CRUD для работы с комментарием к новости;</li>
<li>поиск по тексту и заголовку новости;</li>
<li>просмотр списка новостей с пагинацией;</li>
<li>просмотр списка комментариев к определенной новости с пагинацией;</li>
<li>просмотр новости с комментариями относящимися к ней;</li>
</ul>

Реализована обработка исключений с помощью @ControllerAdvice.  
Даты создания и обновления новостей и комментариев ставятся триггером в базе данных. Используется для обычной работы приложения база данных MySQL. Для тестов - H2.
<ul>
API для работы с новостями :
<li><b>GET</b> http://api/news/{news_id} - получение новости, и связанных с ней комментариев</li>
<li><b>GET</b> http://api/news?offset={page_number}&limit={news_on_page}</li> - получение страницы с новостями, по передаваемым критериям
<li><b>GET</b> http://api/news/search?title={news_title}</li> - получение списка новостей по содержимому заголовка новости
<li><b>GET</b> http://api/news/search?title={news_text}</li> - получение списка новостей по содержимому содерания новости
<li><b>POST</b> http://api/news - сохранение новости на сервере</li>
<li><b>DELETE</b> http://api/news/{news_id} - удаление новости с сервера</li>
<li><b>PUT</b> http://api/news/{news_id} - обновление новости на сервере</li>
</ul>
Для <b>POST</b> и <b>PUT</b> тело запроса:
{ <br>
    "title": " this test title news",<br>
    "text": "this test text news", <br>
}<br>
<ul>
API для работы с комментариями :<br>
<li><b>GET</b> http://api/comments/{comment_id} - получение комментария по id</li>
<li><b>GET</b> http://api/news/{news_id}/comments?offset={page_number}&limit={comments_on_page}</li> - получение страницы с комментариями, по передаваемым критериям к конкретной новости
<li><b>POST</b> http://api/news/{news_id}/comments- сохранение комментария к определенной новости на сервере</li>
<li><b>DELETE</b> http://api/news/{comment_id} - удаление комментария с сервера</li>
<li><b>PUT</b> http://api/comments/{comment_id - обновление комментария на сервере</li>
</ul><br>
Для <b>POST</b> и <b>PUT</b> тело запроса: <br>
{ <br>
    "text": "this test text news", <br>
}<br>










