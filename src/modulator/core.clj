(ns modulator.core
  (:require [modulator.number-utils :as nu]
            [clojure.string :as cstr])
  (:gen-class))

(def RLL-1
  "První verze RLL modulace jak je definováno v návodu na cvičení"
  {"00" "PNNN",
   "01" "NPNN",
   "100" "NNPNNN",
   "101" "PNNPNN",
   "111" "NNNPNN",
   "1100" "NNNNPNNN",
   "1101" "NNPNNPNN"})

(def RLL-2
  "Druhá verze RLL modulace jak je definováno v návodu na cvičení"
  {"11" "PNNN",
   "10" "NPNN",
   "011" "NNPNNN",
   "010" "PNNPNN",
   "000" "NNNPNN",
   "0010" "NNPNNPNN",
   "0011" "NNNNPNNN"})

;;Komplet pravidla
;;0 = PN if předtím 00
;;0 = NN if předtím 10
;;1 = NP
(defn mfm-encode
  "Zakóduje binární text pomocí MFM modulace"
  ([bin-text] (mfm-encode (first bin-text) \0 (rest bin-text) nil))
  ([bit prev tail ret]
   (if (not= bit nil) ;;Dokud máme bity ke kontrole
     (recur
      (first tail) ;;Předáváme další bit na průzkum
      bit ;;Předchozí bude současný bit
      (rest tail) ;;Ocas bude ocas ocasu
      (str ret
           ;;Hlavní logika kde se rozhoduje o tom jaká modulace se použije
           (if (= bit \1)
             "NP"
             (if (= prev \0)
               "PN"
               "NN"))))
     ret)))

(defn fm-encode
  "Zakóduje text pomocí FN modulace"
  [bin-text]
  (cstr/replace
   (cstr/replace bin-text #"0" "PN")
   #"1" "PP"))

(defn -main
  "Vstupní bod, bere 1 argument - text k modulaci"
  [& args]
  (let [text-bin (nu/text-to-bin (first args))
        text-fm (fm-encode text-bin)
        text-mfm (mfm-encode text-bin)
        text-rll1 "nil"
        text-rll2 "nil"]
  (println "Text: " (first args))
  (println "  FM: " text-fm)
  (println " MFM: " text-mfm)))
