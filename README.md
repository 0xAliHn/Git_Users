
**Project implementation details**

*How to test*
Just open the app and search for a user name the it will show all the users list matching with the name you search for

*Technical details and architectural decisions*
- Simple app to show all the users list matching with the name you search for
- Followed MVP clean architecture modularize approach where every feature would be a single feature module in the app. So that our application can be scalable by the time being
it grows
- Project structured with proper clean architecture which is based on MVP and we have ui/view, domain, data and di layer. Each feature module component is independent and
scalable with their responsibility. Every layer has mapper and related model classes where necessary
- We have a core module which is accessible from any feature module or library module in the app.
- Currently core module is responsible for lifecycle handling, subscription of the rx observable, handling api call with call extensions, dagger network module, dagger
scoping and Base classes for all the other feature module component.
- Currently app module contains only MainApplication and AppComponent. And its doesn't have any feature inside the app module. Each feature will be in different module
- We have a feature module gitusers which will show all the user list with image and name matching with the name searched for
- Each layer is abstracted from other layer and forward dependency rules like ui -> domain -> data which is a clean architecture dependency rules. We can not access backward like
domain to presenter or repository to domain. We can use relay, callback, stream or adapter pattern in case we need to communicate within layer or feature module
- Used custom view/presenter for ui and handling lifecycle by own(More control in our hand and easy to figure out the issue). Rxjava for observing events/data and retrofit2 for
network call, Timber for logging, Moshi for response handling
- Unit test for each layer(presenter, domain, data, mapper) which covered completely all business logic and presentation logic using Junit/Jupiter/Mockito without any complexity
since those layer are well separated.