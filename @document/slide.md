http://tonsky.me/blog/readable-clojure/
http://react-toolbox.com/
https://react-bootstrap.github.io/components.html

* edn <-> json : http://cljson.com/
* pretty edn : http://pretty-print.net/


분석하기 어렵지않았다
html / js / react 익숙, 코드를 어지럽게 짜지 않았다.

다른 점
full clojure
boot
reagent
cljs-ajax
no comment 관리어려움 => gitter chatting // slack chating doen't support
new ticket page
events.json => edn with look up table
custom event =>  + google calendar

* google calendar
  - Queries per day	1,000,000
  - Queries per 100 seconds per user	500

# externs
boot-cljs
https://github.com/boot-clj/boot-cljs/wiki/Usage#preamble-and-externs-files

src/cljs/deps.cljs
{:externs ["fullcalendar.ext.js"]}

src/cljs/fullcalendar.ext.js
$.fullCalendar = {};
$.fn.fullCalendar = function(options) {};
