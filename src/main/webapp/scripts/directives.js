'use strict';

angular.module('activitiAdminApp')
    .directive('autoFocus', ['$timeout', function($timeout) {
        return {
            restrict: 'AC',
            link: function(_scope, _element) {
                $timeout(function(){
                    _element[0].focus();
                }, 100);
            }
        };
    }])
    .directive('loadingIndicator', ['$translate', function($translate) {
        return {
            restrict: 'E',
            template: '<div class=\'loading pull-right\' ng-show=\'status.loading\'><div class=\'l1\'></div><div class=\'l2\'></div><div class=\'l2\'></div></div>'
        };
    }])
    .directive('activeMenu', ['$translate', function($translate) {
        return {
            restrict: 'A',
            link: function(scope, element, attrs, controller) {
                var language = attrs.activeMenu;

                scope.$watch(function() {
                    return $translate.uses();
                }, function(selectedLanguage) {
                    if (language === selectedLanguage) {
                        element.addClass('active');
                    } else {
                        element.removeClass('active');
                    }
                });
            }
        };
    }])
    .directive('activeLink', ['$location', function(location) {
        return {
            restrict: 'A',
            link: function(scope, element, attrs, controller) {
                var clazz = attrs.activeLink;
                var path = attrs.href;
                path = path.substring(1); //hack because path does bot return including hashbang
                scope.location = location;
                scope.$watch('location.path()', function(newPath) {
                    if (path === newPath) {
                        element.addClass(clazz);
                    } else {
                        element.removeClass(clazz);
                    }
                });
            }
        };
    }])
    .directive('hourChart', [function () {
        return {
            restrict: 'E',
            templateUrl: 'views/monitoring-hour-chart.html',
            scope: {
                model : '=model',
            },
            link: function ($scope, element, attributes) {
            }
        };
    }])
    .directive('weekdayChart', [function () {
        return {
            restrict: 'E',
            templateUrl: 'views/monitoring-weekday-chart.html',
            scope: {
                model : '=model',
            },
            link: function ($scope, element, attributes) {
            }
        };
    }])
    /** Typeahead scroll hack
     *  directive used in typeahead custom template that enables scrolling 
     *  Must be used in the outer most element of the template and
     *  passed it's index typeahedScrollItem="$parent.$index" */
    .directive('typeaheadScrollItem', function($timeout) {
        return {link: function(scope, element, attrs) {
        
            scope.$watch(attrs.typeaheadScrollItem, function (index) {
                scope.myIndex = index; // this element's index
            
                if (index === 0) { // if first item scroll to top
                    $timeout(function(){
                        element.parent().parent().scrollTop(0);
                    }, 100);
                }
            });
        
            // listen when parent's active index changes
            scope.$parent.$watch('active', function (active) {
                if (scope.myIndex === active) { // if this is the active item
                    var liElement = element.parent();
                    var ulElement = liElement.parent();
                    var elementTop = liElement.offset().top;
                    var minTop = ulElement.offset().top;
                    var maxTop = minTop + ulElement.height();
                    if(!(elementTop > minTop && elementTop < maxTop)) {
                        liElement[0].scrollIntoView();
                    }
                }
            });
        }};
    });
