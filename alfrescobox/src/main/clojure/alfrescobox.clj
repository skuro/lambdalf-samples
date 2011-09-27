(ns alfrescobox
  (:require [alfresco.actions :as a]
            [alfresco.nodes :as n]
            [clojure.pprint :as p])
  (:import [alfresco.actions Action]
           [java.util HashMap]))

(defn email-action-impl [_ action node]
  (println (str  "Acting upon " (:id node)))
  (let [recipients (n/property node "abx:recipients")]
    (println "Recipients are:")
    (map println recipients)))

;; Define a new Action class that can be used in Spring initialization
(defrecord EmailTicketAction []
    Action
    (needs-params [this]
      [(a/param {:name "to"
                 :type "d:text"
                 :mandatory true
                 :multi true
                 :label "email-ticket"})])
    
    (exec [this action node]
      (email-action-impl this action node)))

;; AOT-compile to enable JSF/Spring interop
(gen-class :name alfrescobox.actions.TicketActionHandler
           :extends org.alfresco.web.bean.actions.handlers.BaseActionHandler
           :prefix "tkt-")

(defn tkt-getJSPPath
  "Routes to the correct JSP"
  [this]
  "/jsp/alfrescobox-action.jsp")

(defn tkt-prepareForSave
  "Copies user provided params into to-be-saved props"
  [this ^HashMap act-props ^HashMap repo-props]
  (. repo-props put "tkt-recipients" (. get act-props "tkt-recipients")))

(defn tkt-prepareForEdit
  "Initializes the action form with the currently stored props"
  [this ^HashMap act-props ^HashMap repo-props]
  (println "-----act-props--->")
  (p/pprint act-props)
  (println "<----------------")
  (println "---repo-props--->")
  (clojure.pprint/pprint repo-props)
  (println "<----------------")
  (. act-props put "tkt-recipients" (. repo-props get "tkt-recipients")))

(defn tkt-generateSummary
  [this context wizard act-props]
  (println "test")
  "TBD")
