FROM python

COPY . /app

WORKDIR /app

RUN pip install setuptools --upgrade
RUN pip install flask
RUN pip install tensorflow
RUN pip install pandas

EXPOSE 8888

ENTRYPOINT ["python", "server.py"]