(ns alfrescobox.aspect
  (:require [alfresco.nodes :as n]
            [alfrescobox.ticket :as t]))

(defn downloadable-aspect
  "Applies the abx:downloadable aspect including a randomly generated ticket property"
  [node qname]
  (n/set-properties! node "abx:ticket" (t/gen-ticket)))
