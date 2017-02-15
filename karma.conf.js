module.exports = function(config) {
  config.set({
    frameworks: ['jasmine'],

    reporters: ['kjhtml', 'progress', 'coverage'],

    preprocessors: {
        'src/main/resources/templates/**/*.ftl': ['ng-html2js'],
        'src/main/resources/static/js/components/**/*.js': ['coverage']
    },

    coverageReporter: {
      type : 'html',
      dir : 'coverage/'
    },

    files: [
	  'node_modules/angular/angular.js',
      'node_modules/angular-mocks/angular-mocks.js',
      'node_modules/angular-ui-router/release/angular-ui-router.js',
      'node_modules/localforage/dist/localforage.js',
      'node_modules/ng-storage/ngStorage.js',
      'src/main/resources/static/js/components/main.js',
      'src/main/resources/static/js/components/**/*.js',
      'src/main/resources/templates/**/*.ftl',
      'src/main/resources/static/js/components/**/*.spec.js'
    ],
    
    // web server port8
    port: 9876,


    // enable / disable colors in the output (reporters and logs)
    colors: true,


    // level of logging
    // possible values: config.LOG_DISABLE || config.LOG_ERROR || config.LOG_WARN || config.LOG_INFO || config.LOG_DEBUG
    logLevel: config.LOG_INFO,


    // enable / disable watching file and executing tests whenever any file changes
    autoWatch: true,


    // start these browsers
    // available browser launchers: https://npmjs.org/browse/keyword/karma-launcher
    browsers: ['Chrome'],


    // Continuous Integration mode
    // if true, Karma captures browsers, runs the tests and exits
    singleRun: false
  })
}
