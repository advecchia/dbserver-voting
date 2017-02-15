<div class="generic-container">
    <div class="panel panel-default">
        <!-- Default panel contents -->
        <div class="panel-heading"><span class="lead">List of Winners (A new winner each day at 11:30 AM) </span></div>
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
                    <tr ng-repeat="w in winnersCtrl.getAllWinners() | orderBy:'lunchDate':true">
                        <td>{{w.lunchDate}}</td>
                        <td>{{w.restaurant.name}}</td>
                    </tr>
                    </tbody>
                </table>      
            </div>
        </div>
    </div>
</div>