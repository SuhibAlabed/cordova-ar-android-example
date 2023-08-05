var exec = require("cordova/exec");

exports.openARView = function (success, error, folderName, model, texture) {
  exec(success, error, "ARPlugin", "openARView", [folderName, model, texture]);
};
