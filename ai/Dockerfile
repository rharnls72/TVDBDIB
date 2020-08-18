FROM python

COPY . /app

WORKDIR /app

RUN pip install setuptools --upgrade
RUN pip install flask
RUN pip install tensorflow
RUN pip install pandas

RUN python model_generate.py

EXPOSE 8888

ENTRYPOINT ["python", "-u", "server.py"]