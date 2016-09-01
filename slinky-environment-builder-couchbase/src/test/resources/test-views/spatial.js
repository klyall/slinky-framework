function (doc, meta) {
    if (doc.long && doc.lat && doc.name) {
        emit([doc.long, doc.lat], doc.name);
    }
}