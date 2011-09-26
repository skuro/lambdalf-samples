(ns alfrescobox.util)

;; as seen here: http://blog.raphinou.com/2009/03/generate-random-string-in-clojure.html
(def VALID-CHARS
     (map char (concat (range 48 58) ; 0-9
                       (range 65 91) ; A-Z
                       (range 97 123))))

(defn rand-char
  "Picks a random character from the available sets"
  []
  (rand-nth VALID-CHARS))

(defn rand-str
  "Generates a random string of the given length"
  [length]
  (apply str (take length (repeatedly rand-char))))
