<div class="generic-container">
    <div class="panel panel-default">
        <!-- Default panel contents -->
        <div class="panel-heading"><span class="lead">Login</span></div>
        <div class="panel-body">
            <div class="formcontainer">
                <div class="alert alert-success" role="alert" ng-if="loginCtrl.successMessage">{{loginCtrl.successMessage}}</div>
                <div class="alert alert-danger" role="alert" ng-if="loginCtrl.errorMessage">{{loginCtrl.errorMessage}}</div>
                <form ng-submit="loginCtrl.login()" name="loginForm" class="form-horizontal">
                    <div class="row">
                        <div class="form-group col-md-12">
                            <label class="col-md-2 control-lable" for="lusername">User Name</label>
                            <div class="col-md-7">
                                <input type="text" ng-model="loginCtrl.user.username" id="lusername" class="loginname form-control input-sm" placeholder="Enter the user name" title="At least 3 caracters" required ng-minlength="3" ng-maxlength="20"/>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="form-group col-md-12">
                            <label class="col-md-2 control-lable" for="lpassword">Password</label>
                            <div class="col-md-7">
                                <input type="password" ng-model="loginCtrl.password" id="lpassword" class="loginpassword form-control input-sm" placeholder="Enter the password" title="At least 6 caracters" required ng-minlength="6"/>
                            </div>
                        </div>
                    </div>
                     <div class="row">
                        <div class="form-actions floatRight">
                            <input type="submit" value="Login" class="btn btn-primary btn-sm" ng-disabled="loginForm.$invalid || loginForm.$pristine">
                        </div>
                    </div>
                </form>
                <form ng-submit="loginCtrl.newUser()" name="newUserForm" class="form-horizontal">
                    <div class="row"><br/></div>
                    <div class="row">
                        <div class="form-actions floatRight">
                            <input type="submit" value="New user" class="btn btn-primary btn-sm">
                        </div>
                    </div>
                </form>
            </div>
        </div>    
    </div>
</div>