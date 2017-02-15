<div class="generic-container">
    <div class="panel panel-default">
        <!-- Default panel contents -->
        <div class="panel-heading"><span class="lead">User</span></div>
        <div class="panel-body">
            <div class="formcontainer">
                <div class="alert alert-success" role="alert" ng-if="usersCtrl.successMessage">{{usersCtrl.successMessage}}</div>
                <div class="alert alert-danger" role="alert" ng-if="usersCtrl.errorMessage">{{usersCtrl.errorMessage}}</div>
                <form ng-submit="usersCtrl.submit()" name="usersForm" class="form-horizontal">
                    <input type="hidden" ng-model="usersCtrl.user.id" />
                    <div class="row">
                        <div class="form-group col-md-12">
                            <label class="col-md-2 control-lable" for="uname">Name</label>
                            <div class="col-md-7">
                                <input type="text" ng-model="usersCtrl.user.name" id="uname" class="name form-control input-sm" placeholder="Enter your name" required ng-minlength="3"/>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="form-group col-md-12">
                            <label class="col-md-2 control-lable" for="uusername">User Name</label>
                            <div class="col-md-7">
                                <input type="text" ng-model="usersCtrl.user.username" id="uusername" class="username form-control input-sm" placeholder="Enter your user name" title="At least 3 caracters" required ng-minlength="3" ng-maxlength="20"/>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="form-group col-md-12">
                            <label class="col-md-2 control-lable" for="upassword">Password</label>
                            <div class="col-md-7">
                                <input type="password" ng-model="usersCtrl.password" id="upassword" class="password form-control input-sm" placeholder="Enter the password" title="At least 6 caracters" required ng-minlength="6"/>
                            </div>
                        </div>
                    </div>
                     <div class="row">
                        <div class="form-actions floatRight">
                            <input type="submit"  value="{{!usersCtrl.user.id ? 'Add' : 'Update'}}" class="btn btn-primary btn-sm" ng-disabled="usersForm.$invalid || usersForm.$pristine">
                            <button type="button" ng-click="usersCtrl.reset()" class="btn btn-warning btn-sm" ng-disabled="usersForm.$pristine">Reset Form</button>
                        </div>
                    </div>
                </form>
                <form ng-submit="usersCtrl.backToLogin()" name="backToLogin" class="form-horizontal">
                    <div class="row"><br/></div>
                    <div class="row">
                        <div class="form-actions floatRight">
                            <input type="submit" value="Back to login" class="btn btn-primary btn-sm">
                        </div>
                    </div>
                </form>
            </div>
        </div>    
    </div>
</div>