from flask import Flask, request
import model_load as ml

app = Flask(__name__)

@app.route('/ai/recommend', methods=['GET'])
def getRecommends():
    uno = request.args["uno"]
    return {'data': ml.model_load(int(str(uno)))}

if __name__ == '__main__':
    app.run(port=8888)