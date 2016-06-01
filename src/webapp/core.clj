(ns webapp.core
  (:require [hiccup.page :as page :refer [include-css]]
            [hiccup.form :as form]
            [hiccup.core :refer [h]]
            [ring.util.anti-forgery :refer [anti-forgery-field]]
            [compojure.core :refer [GET POST defroutes]]
            [compojure.route :as route]
            [webapp.addresses :as addresses]
            [clojure.string :as string]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]))



(defn html-body
  [title & contents]
  (page/html5
   [:head
    [:title (h title)]
    (include-css "https://fonts.googleapis.com/css?family=Roboto:300,300italic,700,700italic")
    (include-css "https://cdnjs.cloudflare.com/ajax/libs/normalize/3.0.3/normalize.css")
    (include-css "assets/milligram.min.css")]
   [:body (into [:article {:style "width: 50rem; margin-left: auto; margin-right: auto"}] contents)]))

(defn page
  [title & contents]
  {:status 200
   :headers {"Content-Type" "text/html; charset=utf-8"}
   :body (apply html-body title contents)})

;;; /byebye

(defn byebye
  [request]
  (page "A webapp"
        [:h1 "Goodbye!"]
        [:p "Byebye"]
        [:p [:a {:href "/"} "take me back"]]))

;; example list of people
[:ul
 [:li "joost"]
 [:li "Merik"]]

(defn standard
  [request]
  (page "A webapp"
        [:h1 "Hello!"]
        (form/form-to
         [:post "/delete-people"]
         [:table
          [:thead [:tr [:th "Del"] [:th "Name"] [:th "Phone"]]]
          [:tbody
           (map (fn [p] [:tr
                         [:td (form/check-box "delete[]" false (:name p))]
                         [:td (h (:name p))]
                         [:td (h (:phone p))]])
                (sort-by :name @addresses/db))]]
         (form/submit-button "Delete!"))
        [:p [:a {:href "/new-person"} "Add"]]))

(defn new-person-form
  [request]
  (page "New person"
        (form/form-to [:post "/new-person"]
                      (anti-forgery-field)
                      "Name:"
                      (form/text-field "name")
                      "Phone:"
                      (form/text-field "phone")
                      (form/submit-button "Create!"))))

(defn create-new-person!
  [{{:keys [name phone]} :params :as request}]
  (if (or (string/blank? name) (string/blank? phone))
    (new-person-form request)
    (do (addresses/add-person! (select-keys (:params request) [:name :phone]))
        (page "OK" "Person added"))))

(defn delete-people
  [request]
  (page "OK" "people deleted"))

(defroutes handler
  (GET "/" request
       (standard request))
  (GET "/byebye" request
       (byebye request))
  (GET "/new-person" request
       (new-person-form request))
  (POST "/new-person" request
        (create-new-person! request)))

(defn wrap-secret-access
  [handler]
  (fn [{:keys [:request-method] {:keys [secret]} :params :as request}]
    (if (or (= "yes" secret)
            (= :post request-method))
      (handler request)
      (page "Oh no" "No access for you!"))))


(def handler-with-middleware
  (-> handler
      ;;wrap-secret-access
      (wrap-defaults site-defaults)))
