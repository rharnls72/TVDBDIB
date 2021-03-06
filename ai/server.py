from flask import Flask, request
import model_load as ml
import model_generate as mg

app = Flask(__name__)

@app.route('/ai/recommend', methods=['GET'])
def getRecommends():
    uno = str(request.args["uno"])
    print("Get uno: " + uno)
    uno = int(uno)
    print("Int uno: " + str(uno))
    return {'data': ml.model_load(uno)}

if __name__ == '__main__':
    mg.model_generate()
    app.run(port=8888)