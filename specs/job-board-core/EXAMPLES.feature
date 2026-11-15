# job-board-core — specifications by example
# Scenario tags @w<N> map items to delivery waves (see WAVES.md / BACKLOG.md).

Feature: job-board-core

  @w1 @happy-path
  Scenario: Load bundled job listings
    Given the app ships an assets/jobs.json dataset with at least 5 listings
    When JobRepository loads the dataset
    Then it returns one Job per listing with id, title, company, salary,
      description, qualification, skills, address, vacancy, and date populated

  @w1 @edge-case
  Scenario: Malformed dataset degrades gracefully
    Given an assets/jobs.json that is missing, empty, or not valid JSON
    When JobRepository loads the dataset
    Then it returns an empty list and does not throw

  @w1 @happy-path
  Scenario: Search filters listings
    Given a loaded list containing "Senior Android Developer" at "Acme Corp"
      in Berlin and "Barista" at "Bean House" in Lyon
    When the user searches for "android"
    Then only the Acme Corp listing is returned
    And a search for "lyon" returns only the Bean House listing
    And a search matching nothing returns an empty list

  @w2 @happy-path
  Scenario: Browse and filter the job list
    Given a signed-in job seeker on the home screen
    When the screen loads
    Then every bundled job is visible in the list
    When the user types a keyword in the search box
    Then the list updates live to show only matching jobs
    And when no jobs match, an empty-state message is shown

  @w3 @happy-path
  Scenario: Open job detail
    Given the job list is showing
    When the user taps a job row
    Then JobDetailActivity opens showing that job's title, company,
      designation, salary, description, qualification, skills,
      address, vacancy count, and date
