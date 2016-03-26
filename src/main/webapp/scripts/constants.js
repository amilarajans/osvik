'use strict';

angular.module('activitiAdminApp')
    .constant('gridConstants', {
        defaultTemplate : '<div><div class="ngCellText" title="{{row.getProperty(col.field)}}">{{row.getProperty(col.field)}}</div></div>',
        dateTemplate : '<div><div class="ngCellText" title="{{row.getProperty(col.field) | dateformat:\'full\'}}">{{row.getProperty(col.field) | dateformat}}</div></div>',
        userTemplate : '<div><div class="ngCellText" title="{{row.getProperty(col.field)}}" username="row.getProperty(col.field)"></div></div>',
        groupTemplate : '<div><div class="ngCellText" title="{{row.getProperty(col.field)}}" groupname="row.getProperty(col.field)"></div></div>'
    });


    