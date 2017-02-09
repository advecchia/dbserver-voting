<div class="generic-container">
    <div class="panel panel-default">
        <!-- Default panel contents -->
        <div class="panel-heading"><span class="lead">List of Winners </span></div>
        <div class="panel-body">
            <div class="table-responsive">
                <table class="table table-hover">
                    <thead>
                    <tr>
                        <th>LUNCH DATE</th>
                        <th>NAME</th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr ng-repeat="w in winnersCtrl.getAllWinners()">
                        <td>{{w.lunchDate}}</td>
                        <td>{{w.name}}</td>
                    </tr>
                    </tbody>
                </table>      
            </div>
        </div>
    </div>
</div>