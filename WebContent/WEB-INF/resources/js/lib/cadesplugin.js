$(function() {

	if (window.cades != null)
		return;

	var CADESCOM_CADES_X_LONG_TYPE_1 = 0x5d;
	var CADES_BES = 1;
	
	var CADESCOM_CADES_BES = 1;
	var CADESCOM_CADES_DEFAULT = 0;
	var CADESCOM_CADES_X_LONG_TYPE_1=0x5D;
	
	var CAPICOM_CURRENT_USER_STORE = 2;
	var CAPICOM_MY_STORE = "My";
	var CAPICOM_STORE_OPEN_MAXIMUM_ALLOWED = 2;
	var CAPICOM_STORE_OPEN_READ_ONLY = 0;
	var CAPICOM_CERTIFICATE_FIND_SHA1_HASH = 0;
	var CAPICOM_CERTIFICATE_FIND_SUBJECT_NAME = 1;
	var CAPICOM_ENCODE_BASE64 = 0;
	var CADESCOM_BASE64_TO_BINARY = 1;
	var CAPICOM_HASH_ALGORITHM_SHA1 =0;
	var CADESCOM_HASH_ALGORITHM_CP_GOST_3411 = 100;

	function GetErrorMessage(e) {
		var err = e.message;
		if (!err) {
			err = e;
		} else if (e.number) {
			err += " (" + e.number + ")";
		}
		return err;
	}

	var cadesplugin = $(
			"<object type=\"application/x-cades\" class=\"hiddenObject\" />")
			.appendTo(document.body);

	function CreateObject(name) {
		switch (navigator.appName) {
		case "Microsoft Internet Explorer":
			return new ActiveXObject(name);
		default:
			var userAgent = navigator.userAgent;
			if (userAgent.match(/Trident\/./i)) { // IE11
				return new ActiveXObject(name);
			}
			var cadesobject = cadesplugin[0];
			return cadesobject.CreateObject(name);
		}
	}
	
	function getCadesUrl() {
		return "https://www.cryptopro.ru/products/cades/plugin/get";
		//return $('meta[name=cadesUrl]').attr("value");
	}
	
	function getStrings() {
		return cades.i18n[cades.lang];
	};

	/**
	 * Проверить работоспособность плагина.
	 */
	function CheckForPlugIn() {
		var isPluginLoaded = false;
		var isPluginEnabled = false;
		var isPluginWorked = false;
		// var isActualVersion = false;
		try {
			var oAbout = CreateObject("CAdESCOM.About");
			isPluginLoaded = true;
			isPluginEnabled = true;
			isPluginWorked = true;
			/*
			 * // Это значение будет проверяться сервером при загрузке //
			 * демо-страницы // проверяем версию плагина if ("1.5.1500" <=
			 * oAbout.Version) { isActualVersion = true; }
			 */
		} catch (err) {
			// Объект создать не удалось, проверим, установлен ли
			// вообще плагин. Такая возможность есть не во всех браузерах
			var mimetype = navigator.mimeTypes["application/x-cades"];
			if (mimetype) {
				isPluginLoaded = true;
				var plugin = mimetype.enabledPlugin;
				if (plugin) {
					isPluginEnabled = true;
				}
			}
		}

		if (isPluginWorked) { // плагин работает, объекты создаются
			return true;
			/*
			 * if (isActualVersion) {
			 * document.getElementById('PluginEnabledImg').setAttribute("src",
			 * "Img/green_dot.png");
			 * document.getElementById('PlugInEnabledTxt').innerHTML = "Плагин
			 * загружен"; } else {
			 * document.getElementById('PluginEnabledImg').setAttribute("src",
			 * "Img/yellow_dot.png");
			 * document.getElementById('PlugInEnabledTxt').innerHTML = "Плагин
			 * загружен, но есть более свежая версия"; }
			 */
		} else { // плагин не работает, объекты не создаются
			if (isPluginLoaded) { // плагин загружен
				if (!isPluginEnabled) { // плагин загружен, но отключен
					messageDlg(getStrings().messageTitle, getStrings().loadedAndNotEnabled);
					return false;
					/*
					 * document.getElementById('PluginEnabledImg').setAttribute(
					 * "src", "Img/red_dot.png");
					 * document.getElementById('PlugInEnabledTxt').innerHTML =
					 * "Плагин загружен, но отключен в настройках браузера";
					 */
				} else { // плагин загружен и включен, но объекты не
					// создаются
					messageDlg(getStrings().messageTitle,getStrings().loadedAndFault);
					return false;
					/*
					 * document.getElementById('PluginEnabledImg').setAttribute(
					 * "src", "Img/red_dot.png");
					 * document.getElementById('PlugInEnabledTxt').innerHTML =
					 * "Плагин загружен, но не удается создать объекты.
					 * Проверьте настройки браузера.";
					 */
				}
			} else { // плагин не загружен
				/*
				 * document.getElementById('PluginEnabledImg').setAttribute("src",
				 * "Img/red_dot.png");
				 * document.getElementById('PlugInEnabledTxt').innerHTML =
				 * "Плагин не загружен";
				 */
				messageDlg(getStrings().messageTitle,getStrings().notLoaded);
				return false;
			}
		}
	}

	function OpenStore() {
		var oStore = CreateObject("CAPICOM.Store");
		oStore.Open(CAPICOM_CURRENT_USER_STORE, CAPICOM_MY_STORE,CAPICOM_STORE_OPEN_MAXIMUM_ALLOWED);
		return oStore;
	}
	
	function hashAndSign(certHash, dataToSign) {
		var oStore = OpenStore();

		var oCertificates = oStore.Certificates.Find(
				CAPICOM_CERTIFICATE_FIND_SHA1_HASH, certHash);
		if (oCertificates.Count == 0) {
			messageDlg(getStrings().messageTitle,getStrings().сertificateNotFound + certHash);
			return false;
		}
		var oCertificate = oCertificates.Item(1);
		
		// вычисляем hash
		//dataToSign = window.btoa(unescape(encodeURIComponent(dataToSign)));
		
		var hashObject = CreateObject("CAdESCOM.HashedData");
		hashObject.Algorithm = CAPICOM_HASH_ALGORITHM_SHA1;
		hashObject.DataEncoding = CADESCOM_BASE64_TO_BINARY;
		hashObject.Hash(dataToSign);
		
        var rawSignature = CreateObject("CAdESCOM.RawSignature");
        var signatureHex = rawSignature.SignHash(hashObject, oCertificate);

        VerifySignature(hashObject, oCertificate, signatureHex);
        
        //в base64 и переворачиваем
        signatureHex = window.cades.utils.hexToString(signatureHex);
        signatureHex = window.cades.utils.reverse(signatureHex);
        signatureHex = window.btoa(signatureHex);
        
        var result = {};
        result.signature = signatureHex;
        result.hash = window.btoa(window.cades.utils.hexToString(hashObject.Value));
        result.data = dataToSign;
        return result;
	}
	
	function SignCreate(certHash, dataToSign) {

		var oStore = OpenStore();

		var oCertificates = oStore.Certificates.Find(
				CAPICOM_CERTIFICATE_FIND_SHA1_HASH, certHash);
		if (oCertificates.Count == 0) {
			messageDlg(getStrings().messageTitle,getStrings().сertificateNotFound + certHash);
			return false;
		}
		var oCertificate = oCertificates.Item(1);
		
		var oSigner = CreateObject("CAdESCOM.CPSigner");
		oSigner.Certificate = oCertificate;
		var oSignedData = CreateObject("CAdESCOM.CadesSignedData");
		oSignedData.ContentEncoding = CADESCOM_BASE64_TO_BINARY;
		oSignedData.Content = dataToSign;

		try {
			var sSignedMessage = oSignedData.SignCades(oSigner, CADESCOM_CADES_BES, true);
		} catch (err) {
			messageError(getStrings().errorSign+"<p>"+ GetErrorMessage(err));
			return false;
		}

		oStore.Close();
		return sSignedMessage;
	}

    function VerifySignature(oHashedData, oCertificate, sRawSignature) {
        var oRawSignature = CreateObject("CAdESCOM.RawSignature");
        // Проверяем подпись
        try {
            oRawSignature.VerifyHash(oHashedData, oCertificate, sRawSignature);
        } catch (err) {
            alert("Failed to verify signature. Error: " + GetErrorMessage(err));
            return false;
        }
        return true;
    }
	// --

	var SELECTED_CERT_ATTR = 'cades-selectedCert';

	/**
	 * Выбрать сертификат.
	 */
	function selectCert(callback) {

		var oStore = OpenStore();

		function ret(certHash) {
			oStore.Close();
			callback(certHash);
		}

		var certHash = $.cookie(SELECTED_CERT_ATTR);
		if (certHash != null) {
			var oCerts = oStore.Certificates.Find(
				CAPICOM_CERTIFICATE_FIND_SHA1_HASH, certHash);
			if (oCerts.Count > 0) {
				ret(oCerts.Item(1).Thumbprint);
				return;
			}
		}
		
		frmContent = "<select size=\"5\" style='width:100%'/><p>" +
				"<input type=\"checkbox\"/>"+getStrings().memSertificate;
		
		var buttons = {};
		buttons[getStrings().btnCancel] = function() {
			 $(this).dialog("close");
			 retForm(false);
		}
		buttons[getStrings().btnSelect] = function() {
			try {
				certHash = select.val();
				if (!certHash) {
					messageError(getStrings().selectSertificate);
					return false;
				}
				if (rem[0].checked) {
					$.cookie(SELECTED_CERT_ATTR, certHash);
				}
				retForm(certHash);
				$(this).dialog("close");
			} catch (ex) {
	        	messageError(ex.message);
				if(console) console.log(ex);
			}
		}
		var form = messageDlg(getStrings().titleSelectSertificate,
				frmContent,true,buttons,{
					width:460,
					height:330
				});
		
		var select = form.find("select");
		var rem = form.find("input[type=checkbox]");

		var i, len;
		var certificatesIsEmpty = false;
		try {
			oStore.Certificates.Count
		} catch (e) {
			certificatesIsEmpty = true;
		}
		function retForm(certHash) {
			ret(certHash);
		}

		if (certificatesIsEmpty === false) {
			for (i = 1, len = oStore.Certificates.Count; i <= len; i++) {
				var cert = oStore.Certificates.Item(i);
				$("<option/>") //
					.text(cert.SubjectName) //
					.attr("value", cert.Thumbprint) //
					.appendTo(select);
			}
			form.dialog("open");
		} else {
			messageDlg(getStrings().messageTitle,getStrings().noSertificates);
			retForm(false);
		}
	}

	window.cades = {

		/**
		 * Вычислить ЭП. Подписанное сообщение вернуть в callback.<br>
		 * Если сертификат не выбран, то вернуть null.
		 * 
		 * @param data данные для подписи
		 * @param callback function(signedData)
		 */
		signature: function(data, callback) {

			if (!CheckForPlugIn()) {
				callback(false);
				return;
			}

			selectCert(function(certHash) {

				if (!certHash) {
					callback(false);
					return;
				}

				//var signature = SignCreate(certHash, data);
				//callback(signature,data);
				
				var result = hashAndSign(certHash, data);
				callback(result.signature,result.hash, result.data);
			});
			return false;
		},
		check: function() {
			return CheckForPlugIn()
		}

	};
	// helpers
    window.cades.utils = {
            reverse: function (str) {
                var newStr = '', i;
                for (i = str.length - 1; i >= 0; i--) {
                    newStr += str.charAt(i);
                }
                return newStr;
            },
 
            d2h: function (d) {
                var res = d.toString(16).toUpperCase();
                if (res.length == 1) {
                    res = '0' + res;
                }
                return res;
            },
            h2d: function (h) {
                return parseInt(h, 16);
            },
            stringToHex: function (string) {
                var hex = '';
 
                for (var i = 0; i < string.length; i++) {
                    hex += this.d2h(string.charCodeAt(i));
                }
                return hex;
            },
            hexToString: function (hex) {
                var string = '';
 
                for (var b = 0; b < hex.length; b += 2) {
                    string += String.fromCharCode(parseInt(hex.substr(b, 2), 16));
                }
 
                return string;
            }
    };
    
	window.cades.i18n ={};
	window.cades.i18n.ru = {
		messageTitle:"Оповещение",	
		loadedAndNotEnabled:"Плагин \"КриптоПро ЭЦП\" загружен, но отключен в настройках браузера. Дайте разрешение на выполнение плагина.",
		loadedAndFault:"Плагин \"КриптоПро ЭЦП\" загружен и включен, но работает неверно. Возможно Ваш браузер не поддерживает работу с плагинами",
		notLoaded:"Плагин \"КриптоПро ЭЦП\" не загружен.<p>Загрузите плагин <a href='"+ getCadesUrl() + "'>\"КриптоПро ЭЦП\"</a>.",
		сertificateNotFound:"Сертификат не найден:",
		errorSign:"При формировании ЭП произошла ошибка.",
		errorVerify:"При проверке ЭП произошла ошибка.",
		selectSertificate:"Необходимо выбрать сертификат.",
		noSertificates:"Отсутствуют персональные сертификаты.",	
		titleSelectSertificate:"Выбор сертификата",
		btnCancel:"Отмена",
		btnSelect:"Выбор сертификата",
		memSertificate:"Запомнить выбор"
	};
	
	window.cades.i18n.defaults = window.cades.i18n.ru;
	window.cades.lang = "defaults";

});
