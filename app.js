var createError = require('http-errors');
var express = require('express');
var socket_io    = require('socket.io');
var path = require('path');
var cookieParser = require('cookie-parser');
var logger = require('morgan');

var app = express();
// Socket.io
var io = socket_io();
app.io = io;

var indexRouter = require('./routes/index');
var ordersRouter = require('./routes/orders');
var androidRouter = require('./routes/android');
var protocol = require('./socket/protocol.js')(io);

// view engine setup
app.set('views', path.join(__dirname, 'views'));
app.set('view engine', 'pug');

app.use(logger('dev'));
app.use(express.json());
app.use(express.urlencoded({ extended: false }));
app.use(cookieParser());
app.use(express.static(path.join(__dirname, 'public')));

app.use('/', indexRouter);
app.use('/orders', ordersRouter);
app.use('/android', androidRouter);

// catch 404 and forward to error handler
app.use(function(req, res, next) {
  next(createError(404));
});

// error handler
app.use(function(err, req, res, next) {
  // set locals, only providing error in development
  res.locals.message = err.message;
  res.locals.error = req.app.get('env') === 'development' ? err : {};

  // render the error page
  res.status(err.status || 500);
  res.render('error');
});

module.exports = app;
