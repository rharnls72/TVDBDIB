from flask import Flask
import model_load as ml

app = Flask(__name__)

@app.route('/ai/recommend/<uno>')
def hello_world(uno):
    print('Get uno: ' + uno)
    int_Uno = int(uno)
    print('Int uno: ' + str(int_Uno))
    return {'data': ml.model_load(int(uno))}

if __name__ == '__main__':
    app.run(port=8888)