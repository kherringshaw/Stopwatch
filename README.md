# Learning Objectives

## Modeling

* Modeling state-dependent behavior with [state machine diagrams](http://en.wikipedia.org/wiki/UML_state_machine)
  (see also [here](/lucoodevcourse/stopwatch-android-scala/src/default/doc))
* Distinguishing between view states and (behavioral) model states 
  (see also [here](http://cder.cs.luc.edu/html/chapter.html#interactive-behaviors-and-implicit-concurrency-with-internal-timers))

## Semantics

* [Event-driven/asynchronous program execution](http://en.wikipedia.org/wiki/Event-driven_programming)
* User-triggered input events
* Internal events from background timers
* [Concurrency issues: single-thread rule of accessing/updating the view in the GUI thread](http://stackoverflow.com/questions/11772658/why-is-a-single-threaded-model-used-to-update-the-ui-as-main-thread)
* [Android activity lifecycle](http://developer.android.com/training/basics/activity-lifecycle/starting.html)
  and [preserving state under rotation](http://developer.android.com/training/basics/activity-lifecycle/recreating.html)

## Architecture and Design

* Key architectural issues and patterns
    * [Simple dependency injection (DI)](http://www.martinfowler.com/articles/injection.html)
    * [Model-view-adapter (MVA) architectural pattern](http://en.wikipedia.org/wiki/Model–view–adapter)
    * Mapping MVA to Android
    * [Difference between MVA and model-view-controller (MVC)](https://www.palantir.com/2009/04/model-view-adapter)
    * Distinguishing among dumb, reactive, and autonomous model components
* Key design patterns
    * Implementing event-driven behavior: [Observer pattern](http://sourcemaking.com/design_patterns/observer)
    * Implementing state-dependent behavior: [State pattern](http://sourcemaking.com/design_patterns/state)
    * Hiding complexity in the model from the adapter: [Façade pattern](http://sourcemaking.com/design_patterns/facade)
    * Representing tasks as objects: [Command pattern](http://sourcemaking.com/design_patterns/command)
* [Relevant class-level design principles](http://butunclebob.com/ArticleS.UncleBob.PrinciplesOfOod)
    * Dependency Inversion Principle (DIP)
    * Single Responsibility Principle (SRP)
    * Interface Segregation Principle (ISP)
* Package-level architecture and [relevant principles](http://butunclebob.com/ArticleS.UncleBob.PrinciplesOfOod)
    * [Dependency graph](http://en.wikipedia.org/wiki/Dependency_graph)
      (see also [here](/lucoodevcourse/stopwatch-android-scala/src/default/doc))
    * Stable Dependencies Principle (SDP)
    * Acyclic Dependencies Principle (ADP)
* [Architectural journey](/lucoodevcourse/stopwatch-android-scala/commits)

## Testing

* [Different types of testing](http://en.wikipedia.org/wiki/Software_testing)
    * Component-level unit testing
    * System testing
    * Instrumentation testing
* [Mock-based testing](http://martinfowler.com/articles/mocksArentStubs.html)
* Key design patterns 
    * Factoring out reusable test code: [Testcase Superclass pattern](http://xunitpatterns.com/Testcase%20Superclass.html)
    * Deferring method implementation to subclasses: [Template Method pattern](http://sourcemaking.com/design_patterns/template_method)
    * Allowing subclasses to decide which class to instantiate: [Factory Method pattern](http://sourcemaking.com/design_patterns/factory_method)

* [Test coverage](http://en.wikipedia.org/wiki/Code_coverage) (see also [here](http://martinfowler.com/bliki/TestCoverage.html))

# Building and Running

Please refer to [these notes](http://lucoodevcourse.bitbucket.org/notes/scalaandroiddev.html) for details.

"# Stopwatch" 
