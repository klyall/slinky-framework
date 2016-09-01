function (doc) {
  if (doc.location &&
      doc._class == "example.spatial.domain.ExampleSpatialDocument") {
    emit([doc.location.x, doc.location.y], null);
  }
}