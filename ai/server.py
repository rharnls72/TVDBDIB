from flask import Flask, request
import model_load as ml

app = Flask(__name__)

@app.route('/ai/recommend', methods=['GET'])
def getRecommends():
    uno = request.args["uno"]
    print('Get uno : ' + int(str(uno)))
    return {'data': "너무 힘들어요"}

if __name__ == '__main__':
    app.run(port=8888)