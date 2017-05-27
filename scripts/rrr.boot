#!/usr/bin/env boot

(set-env!
 :dependencies
 '[
   [prismatic/schema "1.1.6"]
   ]
 )

(require '[boot.cli :as cli])
(require '[schema.core :as schema])



(schema/check
 {(schema/enum :eng :jpn) schema/Keyword}
 {:eng :a})

;; https://fullcalendar.io/docs/event_data/Event_Object/
(def Event_Object_schema
  {
   (schema/optional-key :id) schema/Str
   :title schema/Str

   (schema/optional-key :allDay) schema/Bool

   :start Moment_scheme
   (schema/optional-key :end) Moment_scheme
   (schema/optional-key :url) schema/Str
   (schema/optional-key :className) [schema/Str]
   (schema/optional-key :editable) schema/Bool
   (schema/optional-key :startEditable) schema/Bool
   (schema/optional-key :resourceEditable) schema/Bool
   (schema/optional-key :rendering) (schema/enum ["background" "inverse-background"])
   (schema/optional-key :overlap) schema/Bool
   ;; (schema/optional-key :constraint) object
   ;; :source automaticall populated
   (schema/optional-key :color) eventColor_schema
   (schema/optional-key :backgroundColor) eventColor_schema
   (schema/optional-key :borderColor) eventColor_schema
   (schema/optional-key :textColor) eventColor_schema
   }
  )

(def test-input
  {
   :id  "1",
   :title "GDG Korea WebTech meetup",
   :url "https://gdg-korea-webtech.firebaseapp.com/events/meetup-20170223/",
   :start "2017-02-23 19:30:00",
   :end "2017-02-23 22:00:00",
   ;; :address "서울특별시 강남구 논현1동 15-11 Fast Campus 별관 MH bld. 3rd floor",
   ;; :tags "google, gdg, gdgkorea"
   })

(schema/defrecord MeetupContent
    [])

(schema/defrecord MeetupInfo
    [location :- schema/Str
     tags :- [schema/Str]
     contents :- MeetupContent
     event :- Event_Object_schema])

(def x
  (MeetupInfo.
   "서울특별시 강남구 논현1동 15-11 Fast Campus 별관 MH bld. 3rd floor"
   ["google" "gdg" "gdgkorea"]
   (MeetupContent.)
   {
    :id  "1",
    :title "GDG Korea WebTech meetup",
    :url "https://gdg-korea-webtech.firebaseapp.com/events/meetup-20170223/",
    :start "2017-02-23 19:30:00",
    :end "2017-02-23 22:00:00",
    })
  )

(schema/check MeetupInfo x)
(schema/check
 Event_Object_schema
 test-input)

;; https://en.wikipedia.org/wiki/ISO_8601




(def Moment_scheme schema/Str)


;; https://fullcalendar.io/docs/event_rendering/eventColor/
(def eventColor_schema schema/Str)





;;
