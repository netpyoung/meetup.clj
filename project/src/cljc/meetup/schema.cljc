(ns meetup.schema
  (:require [schema.core :as s]))


(def DateScheme
  ":ref
  https://fullcalendar.io/docs/utilities/Moment/
  https://en.wikipedia.org/wiki/ISO_8601
  "
  s/Str)


(def ColorScheme
  ":ref
  https://fullcalendar.io/docs/event_rendering/eventColor/
  "
  s/Str)

(def EventObjectScheme
  ":ref
  https://fullcalendar.io/docs/event_data/Event_Object/
  "
  {(s/optional-key :id)               (s/either s/Str s/Int)
   :title                             s/Str
   (s/optional-key :allDay)           s/Bool
   :start                             DateScheme
   (s/optional-key :end)              DateScheme
   (s/optional-key :url)              s/Str
   (s/optional-key :className)        [s/Str]
   (s/optional-key :editable)         s/Bool
   (s/optional-key :startEditable)    s/Bool
   (s/optional-key :durationEditable) s/Bool
   (s/optional-key :resourceEditable) s/Bool
   (s/optional-key :rendering)        (s/enum "background"
                                              "inverse-background")
   (s/optional-key :overlap)          s/Bool
   ;; (s/optional-key :constraint) an event ID, "businessHours", object
   ;; :source   Event Source Object. Automatically populated.
   (s/optional-key :color)            ColorScheme
   (s/optional-key :backgroundColor)  ColorScheme
   (s/optional-key :borderColor)      ColorScheme
   (s/optional-key :textColor)        ColorScheme})


;; "events.edn"
;; {
;;  :2017-02
;;  ["2017-02-23_helloworld.md"]
;;  }


;; "2017-02-23_helloworld.md"
(def UserMeetupInfo
  {:writer                s/Str
   (s/optional-key :tags) [s/Str]
   :address               s/Str
   :start                 DateScheme
   (s/optional-key :end)  DateScheme
   :title                 s/Str
   :url                   s/Str})


;; "2017-02-23_helloworld.md"
(def UserPresentationInfo
  {:date         [DateScheme]
   :presenter    [s/Str]
   :organization [s/Str]
   :category     [s/Str]
   :tags         [s/Str]
   :infos        [{:language            (s/enum :eng :kor :jpn)
                   :title               s/Str
                   :subtitle            s/Str
                   :presenter-introduce s/Str
                   :session-description s/Str}]})
