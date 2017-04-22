import json

#generic serializible simple object, ref : http://stackoverflow.com/a/15538391
class CleanedObject:
    def toJSON(self):
        return json.dumps(self, default=lambda o: o.__dict__,
                          sort_keys=True, indent=4)
