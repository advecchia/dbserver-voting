'use strict';

var gulp = require('gulp');
var gutil = require('gulp-util');
var watch = require('gulp-watch');
var del = require('del');
var ngAnnotate = require('gulp-ng-annotate');

gulp.task('clean', function() {
  return del(['target/classes/static/js/components']);
});

gulp.task('src', ['clean'], function() {
    return gulp
        .src(['src/main/resources/static/js/**/*.js'])
        .pipe(ngAnnotate())
        .pipe(gulp.dest('target/classes/static/js/components'));
});

gulp.task('watch', function() {
    var watcher = gulp.watch('src/main/resources/static/js/**/*.js', ['src']);
    watcher.on('change', function(event) {
      gutil.log('File '+event.path+' was '+event.type+', running tasks...');
    });
});

gulp.task('default', ['watch']);
