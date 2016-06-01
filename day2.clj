(ns user
  (:require [compojure.core :refer [GET]]))

(def ... nil) ;; placeholder

;;;; Dag 2

;;;; Presentielijst

;;;; Web applicatie

;; https://github.com/zeekat/webapp-scaffolding
;;
;; git clone https://github.com/zeekat/webapp-scaffolding.git

;;;; Project layout

;; project.clj, leiningen
;; boot-clj


;;;; Namespaces, libs, require, refer, import (use)

;; :require is aangeraden, :use is min of meer deprecated

;; In ns form

(ns user
  (:require [hiccup.core :as hiccup])
  (:import (java.util UUID)))

;; "standalone", let op quotes!

(require '[hiccup.core :as hiccup])
(import '[java.util UUID])
(use '[hiccup.core])

;; Files en directories
;; namespaces gescheiden dmv "."
;; "-" in namespace word "_" in filename

;;;; How the web/http works

;;;; Ring spec

;; https://github.com/ring-clojure/ring/blob/master/SPEC
;;
;; Request:
;; :headers, :body (stream), :server-name, :server-port, :uri
;; :request-method, :query-string, :scheme, :protocol
;;
;; Response:
;; :status, :body (stream, seq, file, string), :headers

;;;; Testing

;; lein test
;;
;; running tests in repl
;; reloading issues
;; lein test-refresh

;;;; Macros compojure GET POST

(GET "/pad/dat/matched" request
     ...)

;;;; Sorting

(def people [{:name "Joost"} {:name "Merik"} {:name "Jeremie"}])

(fn [person] (:name person)) == :name

(sort-by :name people)

(defn sort-by-name [coll]
  (sort-by :name coll))

(defn get-name [option]
  (if (= option :ignore-case)
    ...
    ...))

(get-name :ignore-case)

;;;; Forms

;; destructuring
'{{:keys [bla die bla]} :params}


(def r {:params {:name "Joost" :phone "12345" :fake "haha"}})

(:name (:params r))
(get-in r [:params :name])

(select-keys (:params r) [:name :phone])

;; looping

(doseq [name names-collection]
  (delete-person! name))

(reduce + 8 [1 2 3 4])

;;;; Zoeken en aanpassen personen

;; kan op naam of op index in collectie
;; beide niet mooiste oplossing, zou misschien liever een uniek ID gebruiken


;;;; Middleware
;;;; Destructuring



(let [[a b & rest-args] [1 2 3 4]]
  (pr-str a b rest-args))

;;;; Built-in threading macros



;;;; Protocols
;;;; Multi-arg fns
;;;; Multimethods


;;;; Boeken en links

;; Clojure in Action
;; https://www.manning.com/books/clojure-in-action-second-edition
;;
;; http://clojuredocs.org/
;;
;; Oefeningen
;; http://www.tryclj.com/
;; http://www.4clojure.com/

