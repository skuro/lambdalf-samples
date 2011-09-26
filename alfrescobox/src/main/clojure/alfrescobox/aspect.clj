(ns alfrescobox.aspect
  (:require [alfrescobox.ticket :as t]
            [alfrescobox.nodes :as n]))

(defn downloadable-aspect
  "Applies the abx:downloadable aspect including a randomly generated ticket property"
  [node qname]
  (n/set-properties! node "abx:ticket" (t/gen-ticket)))
