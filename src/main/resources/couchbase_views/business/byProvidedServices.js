function (doc, meta) {
  if(doc._class == "servicefinder.data.api.business.Business") {
    for(var i=0;i<doc.providedServices.length;i++){
    	emit(doc.providedServices[i],null);
    }
  }
}