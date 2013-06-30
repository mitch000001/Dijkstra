module Dijkstra

  class Vertice

    attr_reader :weight, :start_point, :end_point

    def self.create(definition)
      start_point = Point.new definition[0]
      end_point = Point.new definition[1]
      Vertice.new(start_point,end_point,definition[2..definition.size].to_i)
    end

    def initialize(start_point,end_point,weight)
      @start_point = start_point
      @end_point = end_point
      @weight = weight
    end

    def to_s
      "#{@start_point}#{@end_point}#{@weight}"
    end

    def <=> obj
      self.to_s <=> obj.to_s
    end

  end

  class Point
    
    attr_reader :name
    attr_accessor :predecessor, :shortest_way

    def initialize(name)
      @name = name
      @visited = false
      @shortest_way = Float::INFINITY
    end

    def visit
      @visited = true
    end

    def visited?
      @visited
    end

    def to_s
      @name
    end

  end

  class Dijkstra

    attr_reader :graph

    def initialize graph
      @graph = {}
      @calculated_predecessors = {}
      graph.each do |vertice|
        unless vertice.respond_to? :start_point
          vertice = Vertice.create(vertice)
        end
        start_point_name = vertice.start_point.name
        if @graph[start_point_name].nil?
          @graph[start_point_name] = []
        end
        @graph[start_point_name] << vertice
      end
    end

    def shortest_path_from_start end_point, predecessors
      end_point = Point.new(end_point.to_s) unless end_point.respond_to? :name
      way = [end_point]
      current_point = end_point
      unless (previous_point = predecessors[current_point.name]).nil?
        current_point = previous_point
        way.unshift previous_point
      end
      way
    end
 
    def shortest_path start_point,end_point
      predecessors = dijkstra(start_point)
      shortest_path_from_start(end_point,predecessors)
    end
 
    def is_reachable_from? start_point, end_point
      shortest_path(start_point,end_point).size > 1
    end

    private

    def dijkstra(start_point)
      start_point = Point.new(start_point.to_s) unless start_point.respond_to? :name
      return @calculated_predecessors[start_point.name] if @calculated_predecessors[start_point.name]
      distances = {}
      predecessors = {}
      points_to_visit = @graph.keys
      points_to_visit.each do |point|
        distances[point] = Float::INFINITY
        predecessors[point] = nil
      end
      distances[start_point.name] = 0
      predecessors[start_point.name] = start_point
      unless points_to_visit.empty?
        minimum = distances.min {|v1,v2| v1[1] <=> v2[1]}[0]
        points_to_visit.delete minimum
        @graph[minimum].each do |vertice|
          end_point_name = vertice.end_point.name
          if points_to_visit.include? end_point_name
            new_distance = distances[minimum] + vertice.weight
            if new_distance < distances[end_point_name]
              distances[end_point_name] = new_distance
              predecessors[end_point_name] = vertice.start_point
            end
          end
        end
      end
      @calculated_predecessors[start_point.name] = predecessors
      predecessors
    end
 
  end

  def self.shortest_path start_point,end_point,graph
    dijkstra(graph).shortest_path start_point,end_point
  end

  def self.is_reachable_from? start_point, end_point, graph
    dijkstra(graph).is_reachable_from? start_point, end_point
  end

  private

  def self.dijkstra graph
    @dijkstras ||= {}
    graph_key = graph.sort.reduce("") {|string,e|string << e}
    @dijkstras[graph_key] ||= Dijkstra.new(graph)
  end

end
