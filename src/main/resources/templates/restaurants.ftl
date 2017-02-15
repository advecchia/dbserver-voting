<div class="generic-container">
    <div class="panel panel-default">
        <!-- Default panel contents -->
        <div class="panel-heading"><span class="lead">Restaurant</span></div>
        <div class="panel-body">
            <div class="formcontainer">
                <div class="alert alert-success" role="alert" ng-if="restaurantsCtrl.successMessage">{{restaurantsCtrl.successMessage}}</div>
                <div class="alert alert-danger" role="alert" ng-if="restaurantsCtrl.errorMessage">{{restaurantsCtrl.errorMessage}}</div>
                <form ng-submit="restaurantsCtrl.submit()" name="restaurantsForm" class="form-horizontal">
                    <input type="hidden" ng-model="restaurantsCtrl.restaurant.id" />
                    <div class="row">
                        <div class="form-group col-md-12">
                            <label class="col-md-2 control-lable" for="rname">Name</label>
                            <div class="col-md-7">
                                <input type="text" ng-model="restaurantsCtrl.restaurant.name" id="rname" class="restaurantname form-control input-sm" placeholder="Enter the restaurant name" required ng-minlength="3"/>
                            </div>
                        </div>
                    </div>
 
                    <div class="row">
                        <div class="form-actions floatRight">
                            <input type="submit"  value="{{!restaurantsCtrl.restaurant.id ? 'Add' : 'Update'}}" class="btn btn-primary btn-sm" ng-disabled="restaurantsForm.$invalid || restaurantsForm.$pristine">
                            <button type="button" ng-click="restaurantsCtrl.reset()" class="btn btn-warning btn-sm" ng-disabled="restaurantsForm.$pristine">Reset Form</button>
                        </div>
                    </div>
                </form>
            </div>
        </div>    
    </div>

    <div class="panel panel-default">
        <!-- Default panel contents -->
        <div class="panel-heading"><span class="lead">List of Restaurants </span></div>
        <div class="panel-body">
            <div class="table-responsive">
                <table class="table table-hover">
                    <thead>
                    <tr>
                        <th>NAME</th>
                        <th width="100"></th>
                        <th width="100"></th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr ng-repeat="r in restaurantsCtrl.getAllRestaurants() | orderBy:'name'">
                        <td>{{r.name}}</td>
                        <td><button type="button" ng-click="restaurantsCtrl.editRestaurant(r.id)" class="btn btn-success custom-width">Edit</button></td>
                        <td><button type="button" ng-click="restaurantsCtrl.removeRestaurant(r.id)" class="btn btn-danger custom-width">Remove</button></td>
                    </tr>
                    </tbody>
                </table>      
            </div>
        </div>
    </div>
</div>