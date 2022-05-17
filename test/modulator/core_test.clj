(ns modulator.core-test
  (:require [clojure.test :refer :all]
            [modulator.core :refer :all]
            [modulator.number-utils :refer :all]))

(deftest number-utils-test
  (testing "Binární konverze"
    (is (= "101010" (do-binarky 42)))
    (is (= "1000101" (do-binarky 69)))
    (is (= "1100001001" (do-binarky 777))))
  (testing "Zarovnávání na bajt"
    (is (= "11111111" (zarovnej-na-byte "11111111")) "Selhání už přesné délky")
    (is (= "00000101" (zarovnej-na-byte "101")) "Selhání výchozí délky")
    (is (= "0000000000101010" (zarovnej-na-byte "101010" 2)) "Selhání 16 bit délky")
    (is (= "00000000000000000000000101011100" (zarovnej-na-byte "101011100" 4)) "Selhání 32bit délky")
    (is (= "0000000000000000000000000000000000000000000000000000000110011001" (zarovnej-na-byte "110011001", 8)) "Selhání 64 délky"))
  (testing "Znak na bin"
    (is (= "01100001" (znak-to-bin \a)) "Malý znak")
    (is (= "01011010" (znak-to-bin \Z)) "velký znak")) ;;Todo přidat Test na házení chyb při znaku mimo a-Z
  (testing "Text na bin"
    (is (= "01000110011001010110110001101001011000110110100101100001" (text-to-bin "Felicia")) "Text malé i velké")
    (is (= "01001001010101110101010001000011010010010101001001000100" (text-to-bin "IWTCIRD")) "All caps text")
    (is (= "01100101011011010110000101100011011100110011010001100101011101100110010101110010" (text-to-bin "emacs4ever")) "Malá písmena + čísla")))

(deftest encode-tests
  (testing "FM modulace"
    ;;Moc jich není, mě se to nechce převádět ručně
    (is (= "PPPNPPPPPNPN" (fm-encode "101100")) "Ukázková FM"))
  (testing "MFM modulace"
    (is (= "NPNNNPNPNNPN" (mfm-encode "101100")) "Ukázková MFM"))
  (testing "RLL modulace"
    (is (= "NPNNNPNNPNNNPNNNNPNNNNNNPNNNNNPNNNNNNNPNNNPNNNPNNPNNPNNPNNPNNNPNNPNNNNNNPNNNNNPNNPNNNNPNNNPNNPNN"
           (rll-encode RLL-1 "010100000111001001100001011010010111001101100101")) "RLL-1")
    (is (= "PNNPNNNNNPNNPNNNNNPNNNNNPNNPNNNPNNPNNNNNPNNNNNNPNNPNNNNNPNNPNNNPNNPNNNNPNNNNPNNNNNPNNNNPNNNPNNNNNNPNNNPNNPNNPNNPNNPNNNNNNPNNPNNPNN"
           (rll-encode RLL-2 "0100001101100101011011000110010101110011011101000110100101100001")) "RLL-2")))
