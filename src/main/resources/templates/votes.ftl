<div class="generic-container">
    <div class="panel panel-default">
        <!-- Default panel contents -->
        <div class="panel-heading"><span class="lead">Vote for today lunch </span></div>
        <div class="panel-body">
            <div class="formcontainer">
                <div class="alert alert-success" role="alert" ng-if="votesCtrl.successMessage">{{votesCtrl.successMessage}}</div>
                <div class="alert alert-danger" role="alert" ng-if="votesCtrl.errorMessage">{{votesCtrl.errorMessage}}</div>
                <form ng-submit="votesCtrl.submit()" name="votesForm" class="form-horizontal">
                    <div class="row">
                        <div class="form-group col-md-12">
                            <div class="col-md-6">
                            	<label class="col-md-4 control-lable" for="rname">Restaurant Name</label>
                            	<select name="rname" id="rname" ng-model="votesCtrl.restaurant" class="restaurantname form-control input-sm" required>
							        <option ng-repeat="restaurant in votesCtrl.restaurants" value="{{restaurant}}">{{restaurant.name}}</option>
							    </select>
                            </div>
                        </div>
                    </div>
 
                    <div class="row">
                        <div class="form-actions floatRight">
                            <input type="submit"  value="Vote" class="btn btn-primary btn-sm" ng-disabled="votesForm.$invalid || votesForm.$pristine">
                        </div>
                    </div>
                </form>
            </div>
        </div>    
    </div>
    
    <div class="panel panel-default">
        <!-- Default panel contents -->
        <div class="panel-heading"><span class="lead">List of Votes</span></div>
        <div class="panel-body">
            <div class="table-responsive">
                <table class="table table-hover">
                    <thead>
                    <tr>
                        <th>USER NAME</th>
                        <th>RESTAURANT NAME</th>
                        <th>VOTING DATE</th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr ng-repeat="v in votesCtrl.getAllVotes() | orderBy:'votingDate':true">
                        <td>{{v.user.name}}</td>
                        <td>{{v.restaurant.name}}</td>
                        <td>{{v.votingDate}}</td>
                    </tr>
                    </tbody>
                </table>      
            </div>
        </div>
    </div>
</div>